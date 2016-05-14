package name.caiyao.tencentsport;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    EditTextPreference mEditTextPreference;
    public final String SETTING_CHANGED = "name.caiyao.tencentsport.SETTING_CHANGED";

    public SettingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(1);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        addPreferencesFromResource(R.xml.preference);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditTextPreference = (EditTextPreference) findPreference("magnification");
        changeSummary();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        changeSummary();
        getKey();
        return true;
    }

    public void getKey() {
        Intent intent = new Intent(SETTING_CHANGED);
        intent.putExtra("weixin", getPreferenceManager().getSharedPreferences().getBoolean("weiixn", true));
        intent.putExtra("qq", getPreferenceManager().getSharedPreferences().getBoolean("qq", true));
        intent.putExtra("magnification", getPreferenceManager().getSharedPreferences().getString("magnification", "100"));
        intent.putExtra("autoincrement", getPreferenceManager().getSharedPreferences().getBoolean("autoincrement", false));
        intent.putExtra("ledong", getPreferenceManager().getSharedPreferences().getBoolean("ledong", true));
        intent.putExtra("yuedong", getPreferenceManager().getSharedPreferences().getBoolean("yuedong", true));
        intent.putExtra("pingan", getPreferenceManager().getSharedPreferences().getBoolean("pingan", true));
        if (getActivity() != null) {
            getActivity().sendBroadcast(intent);
        }
    }

    private void changeSummary() {
        if (mEditTextPreference != null)
            mEditTextPreference.setSummary(getPreferenceManager().getSharedPreferences().getString("magnification", "1000"));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        changeSummary();
        getKey();
    }
}
