package com.boltuix.androidpreferences

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener,
    Preference.OnPreferenceClickListener {

    companion object {
        private val TAG = SettingsFragment::class.java.simpleName
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)

        preferenceManager.sharedPreferences!!.registerOnSharedPreferenceChangeListener(this)

        findPreference<Preference>("key_preference_nested")?.onPreferenceClickListener = this

        findPreference<EditTextPreference>("key_preference_edit_text")
            ?.setOnBindEditTextListener { editText ->
                editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                editText.maxLines = 1
                editText.imeOptions = EditorInfo.IME_ACTION_DONE
            }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.d(TAG, "on shared preferences changed..key: $key")

        when (key) {
            "key_preference_switch" -> {
                val value = sharedPreferences?.getBoolean(key, false)
                findPreference<Preference>("key_preference_switch_text")?.isVisible = value ?: false
                Log.d(TAG, "updated value: $value")
            }
            "key_preference_check_box" -> {
                val value = sharedPreferences?.getBoolean(key, false)
                Log.d(TAG, "updated value: $value")
            }
            "key_preference_seekbar" -> {
                val value = sharedPreferences?.getInt(key, -1)
                Log.d(TAG, "updated value: $value")
            }
            "key_preference_edit_text" -> {
                sharedPreferences?.getString(key, "")?.let { Log.d(TAG, "updated value: $it") }
            }
            "key_preference_list" -> {
                sharedPreferences?.getString(key, "")?.let { Log.d(TAG, "updated value: $it") }
            }
            "key_preference_multi_select_list" -> {
                sharedPreferences?.getStringSet(key, emptySet())
                    ?.let { Log.d(TAG, "updated value: $it") }
            }
        }
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        when (preference.key) {
            "key_preference_nested" -> {
                Log.d(TAG, "received click for key preference 3")
               // findNavController().navigate(R.id.action_nav_pref_setting_to_nested_setting)
            }
        }
        return true
    }
}