/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.android.apps.mytracks;

import com.google.android.apps.mytracks.util.PreferencesUtils;
import com.google.android.apps.uvg.fragment.ChooseSPFLevelDialogFragment;
import com.google.android.apps.uvg.fragment.ChooseSPFLevelDialogFragment.ChooseSPFLevelCaller;
import com.google.android.apps.uvg.fragment.ChooseSkinTypeDialogFragment;
import com.google.android.apps.uvg.fragment.ChooseSkinTypeDialogFragment.ChooseSkinTypeCaller;
import com.google.android.apps.uvg.utils.SPFLevelIconUtils;
import com.google.android.apps.uvg.utils.SkinTypeIconUtils;
import com.google.android.maps.mytracks.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * An activity that let's the user see and edit the user editable track meta
 * data such as track name, activity type, and track description.
 * 
 * @author Leif Hendrik Wilden
 */
public class UVGProfileActivity extends AbstractMyTracksActivity 
implements ChooseSkinTypeCaller,ChooseSPFLevelCaller {

  private static final String TAG = UVGProfileActivity.class.getSimpleName();
  private static final String ICON_VALUE_SKIN_TYPE = "icon_value_skin_type";
  private static final String ICON_VALUE_SPF = "icon_value_spf";

  private String skinTypeIconValue,spfLevelIconValue;
  private EditText name;
  SharedPreferences sharedPreferences;
  private AutoCompleteTextView skinType;
  private ImageButton skinTypeIcon;


  /*
   * Note that sharedPreferenceChangeListenr cannot be an anonymous inner class.
   * Anonymous inner class will get garbage collected.
   */

      
  private AutoCompleteTextView spfLevel;
  private ImageButton spfLevelIcon;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);

    sharedPreferences = getSharedPreferences(Constants.SETTINGS_NAME,Context.MODE_PRIVATE);
    
    String prefereneName = PreferencesUtils.getStringConventional(UVGProfileActivity.this,Constants.PROFILE_NAME,null);
    name = (EditText) findViewById(R.id.uvg_profile_name);
    name.setText(prefereneName);

    String preferenceSkinType = PreferencesUtils.getStringConventional(UVGProfileActivity.this, Constants.PROFILE_SKIN_TYPE,null);
    skinType = (AutoCompleteTextView) findViewById(R.id.uvg_profile_skin_type);
    skinType.setText(preferenceSkinType);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        this, R.array.skin_types, android.R.layout.simple_dropdown_item_1line);
    skinType.setAdapter(adapter);
    skinType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setSkinTypeIcon(SkinTypeIconUtils.getIconValue(
            UVGProfileActivity.this, (String) skinType.getAdapter().getItem(position)));
      }
    });
    skinType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
          setSkinTypeIcon(SkinTypeIconUtils.getIconValue(
              UVGProfileActivity.this, skinType.getText().toString()));
        }
      }
    });

    skinTypeIcon = (ImageButton) findViewById(R.id.uvg_profile_skin_type_icon);
    skinTypeIcon.setOnClickListener(new View.OnClickListener() {
        @Override
      public void onClick(View v) {
        new ChooseSkinTypeDialogFragment().show(getSupportFragmentManager(),
            ChooseSkinTypeDialogFragment.CHOOSE_SKIN_TYPE_DIALOG_TAG);
      }
    });

    
    
    String preferenceSPFLevel = PreferencesUtils.getStringConventional(UVGProfileActivity.this,Constants.PROFILE_SPF_FACTOR,null);
    spfLevel = (AutoCompleteTextView) findViewById(R.id.uvg_profile_spf_level);
    spfLevel.setText(preferenceSPFLevel);
    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
        this, R.array.spf_levels, android.R.layout.simple_dropdown_item_1line);
    spfLevel.setAdapter(adapter2);
    spfLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setSPFLevelIcon(SPFLevelIconUtils.getIconValue(
            UVGProfileActivity.this, (String) spfLevel.getAdapter().getItem(position)));
      }
    });
    spfLevel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
          setSPFLevelIcon(SPFLevelIconUtils.getIconValue(
              UVGProfileActivity.this, spfLevel.getText().toString()));
        }
      }
    });

    spfLevelIcon = (ImageButton) findViewById(R.id.uvg_profile_spf_level_icon);
    spfLevelIcon.setOnClickListener(new View.OnClickListener() {
        @Override
      public void onClick(View v) {
        new ChooseSPFLevelDialogFragment().show(getSupportFragmentManager(),
            ChooseSPFLevelDialogFragment.CHOOSE_SPF_LEVEL_DIALOG_TAG);
      }
    });
    
    skinTypeIconValue = null;
    spfLevelIconValue = null;
    
    if (bundle != null) {
      skinTypeIconValue = bundle.getString(ICON_VALUE_SKIN_TYPE);
      spfLevelIconValue = bundle.getString(ICON_VALUE_SPF);
    }
    
    if (skinTypeIconValue == null) {
      String preferenceSkinTypeIcon = PreferencesUtils.getStringConventional(UVGProfileActivity.this,Constants.PROFILE_SKIN_TYPE_ICON,null);
      skinTypeIconValue = preferenceSkinTypeIcon;
    }

    if (spfLevelIconValue == null) {
      String preferenceSPFLevelIcon = PreferencesUtils.getStringConventional(UVGProfileActivity.this, Constants.PROFILE_SPF_FACTOR_ICON,null);
      spfLevelIconValue = preferenceSPFLevelIcon;
    }
    setSkinTypeIcon(skinTypeIconValue);
    setSPFLevelIcon(spfLevelIconValue);

    Button save = (Button) findViewById(R.id.track_edit_save);
    save.setOnClickListener(new View.OnClickListener() {
        @Override
      public void onClick(View v) {
        
        String nameVal = name.getText().toString(),
            skinTypeVal = skinType.getText().toString(),
            skinTypeIconVal = SkinTypeIconUtils.getIconValue(UVGProfileActivity.this, skinTypeVal),
            spfLevelVal = spfLevel.getText().toString(),
            spfLevelIconVal = SPFLevelIconUtils.getIconValue(UVGProfileActivity.this, spfLevelVal);
            
        //update shared preferences since it's only one profile to the app
        
        PreferencesUtils.setStringConventional(getBaseContext(), Constants.PROFILE_NAME, nameVal);
        PreferencesUtils.setStringConventional(getBaseContext(), Constants.PROFILE_SKIN_TYPE, skinTypeVal);
        PreferencesUtils.setStringConventional(getBaseContext(), Constants.PROFILE_SPF_FACTOR, spfLevelVal);
        PreferencesUtils.setStringConventional(getBaseContext(), Constants.PROFILE_SKIN_TYPE_ICON, skinTypeIconVal);
        PreferencesUtils.setStringConventional(getBaseContext(), Constants.PROFILE_SPF_FACTOR_ICON, spfLevelIconVal);
        
        finish();
      }
    });

    Button cancel = (Button) findViewById(R.id.track_edit_cancel);
    setTitle(R.string.menu_edit);
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
      public void onClick(View v) {
        finish();
      }
    });
    cancel.setVisibility(View.VISIBLE);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(ICON_VALUE_SKIN_TYPE, skinTypeIconValue);
    outState.putString(ICON_VALUE_SPF, spfLevelIconValue);
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.uvg_profile;
  }
  /**
   * Sets the activity type icon.
   * 
   * @param value the icon value
   */
  private void setSkinTypeIcon(String value) {
    skinTypeIconValue = value;
    skinTypeIcon.setImageResource(SkinTypeIconUtils.getIconDrawable(value));
  }
  
  /**
   * Sets the activity type icon.
   * 
   * @param value the icon value
   */
  private void setSPFLevelIcon(String value) {
    spfLevelIconValue = value;
    spfLevelIcon.setImageResource(SPFLevelIconUtils.getIconDrawable(value));
  }

  @Override
  public void onChooseSkinTypeDone(String value) {
    skinType.setText(getString(SkinTypeIconUtils.getIconSkinType(value)));
    setSkinTypeIcon(value);
  }
  @Override
  public void onChooseSPFLevelDone(String value) {
    spfLevel.setText(getString(SPFLevelIconUtils.getIconSPFLevel(value)));
    setSPFLevelIcon(value);
  }

}
