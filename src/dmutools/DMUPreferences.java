/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmutools;

import java.util.prefs.Preferences;

/**
 *
 * @author mats
 */
class DMUPreferences {

    private Preferences prefs = Preferences.userNodeForPackage(getClass());
    
    private String templateFileName;
    private final String TEMPLATE_FILE_NAME_KEY  = "Default template file";
    private final String DEFAULT_TEMPLATE_FILE_NAME = "E:\\Mats\\VLISTDMU.ods";

    private static final DMUPreferences singleton = new DMUPreferences();

    private DMUPreferences() {}

    public static DMUPreferences getInstance() {
        return singleton;
    }

    public String getTemplateFileName() {
        return prefs.get(TEMPLATE_FILE_NAME_KEY, DEFAULT_TEMPLATE_FILE_NAME);
    }

    public void setTemplateFileName(String templateFileName) {
        prefs.put(TEMPLATE_FILE_NAME_KEY, templateFileName);
    }

    public void showPrefDialog() {
        PrefsDialog prefsDialog = new PrefsDialog(null, true);
        prefsDialog.setTemplateFileName( getTemplateFileName() );
        prefsDialog.setVisible(true);
        int retval = prefsDialog.getReturnStatus();
        if ( retval == PrefsDialog.RET_OK ) {
            setTemplateFileName( prefsDialog.getTemplateFileName() );
        }
    }

}

