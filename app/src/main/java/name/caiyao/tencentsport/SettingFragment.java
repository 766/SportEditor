package name.caiyao.tencentsport;


import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    EditTextPreference mEditTextPreference, maxditTextPreference;
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
        maxditTextPreference = (EditTextPreference) findPreference("max");
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
        intent.putExtra("weixin", (getPreferenceManager().getSharedPreferences().getBoolean("weiixn", true)));
        intent.putExtra("qq", (getPreferenceManager().getSharedPreferences().getBoolean("qq", true)));
        intent.putExtra("magnification", (getPreferenceManager().getSharedPreferences().getString("magnification", "100")));
        intent.putExtra("autoincrement", (getPreferenceManager().getSharedPreferences().getBoolean("autoincrement", false)));
        intent.putExtra("max", (getPreferenceManager().getSharedPreferences().getString("max", "100000")));
        if (getActivity() != null) {
            getActivity().sendBroadcast(intent);
        }
    }

    private void changeSummary() {
        mEditTextPreference.setSummary(getPreferenceManager().getSharedPreferences().getString("magnification", "1000"));
        maxditTextPreference.setSummary(getPreferenceManager().getSharedPreferences().getString("max", "100000"));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("icon")) {
            changeIcon(sharedPreferences.getBoolean("icon", false));
        }
        changeSummary();
        getKey();
    }

    private void changeIcon(boolean isShow) {
        PackageManager packageManager = getActivity().getPackageManager();
        ComponentName componentName = new ComponentName(getActivity().getPackageName(), getActivity().getPackageName() + ".MainActivity");
        if (!isShow)
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        else
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }
}
