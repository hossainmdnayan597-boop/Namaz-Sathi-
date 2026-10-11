package com.example.notification

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

object AzanAlarmSoundManager {

    private const val TAG = "AzanAlarmSoundManager"
    private var playbackJob: Job? = null
    private var currentAudioTrack: AudioTrack? = null
    private var fallbackRingtone: Ringtone? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _activePrayerName = MutableStateFlow<String?>(null)
    val activePrayerName: StateFlow<String?> = _activePrayerName.asStateFlow()

    // Maqam Bayati traditional frequencies for Azan Takbeerat
    // D4 = 293.66 Hz, Eb4 = 311.13 Hz, F4 = 349.23 Hz, G4 = 392.00 Hz, A4 = 440.00 Hz
    private val AZAN_TAKBEER_NOTES = listOf(
        // "Al-laahu Akbar" (Phrase 1)
        Pair(293.66, 700),  // Al-
        Pair(349.23, 1100), // laa-hu
        Pair(392.00, 800),  // Ak-
        Pair(349.23, 1400), // bar...
        Pair(0.0, 400),     // Pause

        // "Al-laahu Akbar" (Phrase 2)
        Pair(293.66, 700),  // Al-
        Pair(349.23, 1100), // laa-hu
        Pair(392.00, 900),  // Ak-
        Pair(349.23, 1600), // bar...
        Pair(0.0, 600),     // Pause

        // "Ash-hadu alla ilaha illallah"
        Pair(349.23, 800),  // Ash-hadu
        Pair(392.00, 900),  // al-laa
        Pair(440.00, 1200), // i-laa-ha
        Pair(392.00, 1500), // il-lal-laah
        Pair(0.0, 600),     // Pause

        // "Hayya 'alas-Salah"
        Pair(392.00, 900),  // Hay-ya
        Pair(440.00, 1200), // 'alas-Sa-
        Pair(349.23, 1600), // laah...
        Pair(0.0, 500),

        // Final Takbeer: "Allahu Akbar, La ilaha illallah"
        Pair(293.66, 800),
        Pair(349.23, 1200),
        Pair(293.66, 1800),
        Pair(0.0, 500)
    )

    fun playAzan(context: Context, prayerName: String = "ওয়াক্ত", isTest: Boolean = false) {
        stopAzan()
        _isPlaying.value = true
        _activePrayerName.value = prayerName

        playbackJob = CoroutineScope(Dispatchers.Default).launch {
            try {
                val sampleRate = 22050
                val minBufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()

                val audioFormat = AudioFormat.Builder()
                    .setSampleRate(sampleRate)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()

                val track = AudioTrack.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setAudioFormat(audioFormat)
                    .setBufferSizeInBytes(minBufferSize * 2)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                currentAudioTrack = track
                track.play()

                val repetitions = if (isTest) 1 else 2
                for (rep in 0 until repetitions) {
                    if (!isActive) break

                    for ((freq, durationMs) in AZAN_TAKBEER_NOTES) {
                        if (!isActive) break

                        val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
                        val samples = ShortArray(numSamples)

                        if (freq > 0.0) {
                            val attackSamples = (sampleRate * 0.08).toInt().coerceAtMost(numSamples / 3)
                            val releaseSamples = (sampleRate * 0.15).toInt().coerceAtMost(numSamples / 2)

                            for (i in 0 until numSamples) {
                                // Envelope shaping
                                val envelope = when {
                                    i < attackSamples -> i.toDouble() / attackSamples
                                    i > numSamples - releaseSamples -> (numSamples - i).toDouble() / releaseSamples
                                    else -> 1.0
                                }

                                // Fundamental + rich warm harmonics (Bayati vocal timbre)
                                val angle1 = 2.0 * PI * i * freq / sampleRate
                                val angle2 = 2.0 * PI * i * (freq * 2.0) / sampleRate
                                val angle3 = 2.0 * PI * i * (freq * 3.0) / sampleRate

                                val sampleValue = (
                                    sin(angle1) * 0.70 +
                                    sin(angle2) * 0.20 +
                                    sin(angle3) * 0.10
                                ) * envelope * 24000.0

                                samples[i] = sampleValue.toInt().coerceIn(-32768, 32767).toShort()
                            }
                        } else {
                            // Silence
                            samples.fill(0)
                        }

                        track.write(samples, 0, samples.size)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "AudioTrack synthesis failed, falling back to RingtoneManager", e)
                try {
                    val alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                        ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    fallbackRingtone = RingtoneManager.getRingtone(context.applicationContext, alertUri)
                    fallbackRingtone?.play()
                } catch (rEx: Exception) {
                    Log.e(TAG, "Fallback ringtone failed", rEx)
                }
            } finally {
                stopInternal()
            }
        }
    }

    fun stopAzan() {
        playbackJob?.cancel()
        playbackJob = null
        stopInternal()
    }

    private fun stopInternal() {
        try {
            currentAudioTrack?.stop()
            currentAudioTrack?.release()
        } catch (_: Exception) {}
        currentAudioTrack = null

        try {
            fallbackRingtone?.stop()
        } catch (_: Exception) {}
        fallbackRingtone = null

        _isPlaying.value = false
        _activePrayerName.value = null
    }
}
