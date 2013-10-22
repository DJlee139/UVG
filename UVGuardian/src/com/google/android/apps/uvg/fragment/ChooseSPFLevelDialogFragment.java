/*
 * Copyright 2013 Google Inc.
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

package com.google.android.apps.uvg.fragment;

import com.google.android.apps.uvg.utils.SPFLevelIconUtils;
import com.google.android.maps.mytracks.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A DialogFragment to choose an spf level.
 * 
 * @author apoorvn
 */
public class ChooseSPFLevelDialogFragment extends DialogFragment {

  /**
   * Interface for caller of this dialog fragment.
   * 
   * @author apoorvn
   */
  public interface ChooseSPFLevelCaller {

    /**
     * Called when choose spf level is done.
     */
    public void onChooseSPFLevelDone(String iconValue);
  }

  public static final String CHOOSE_SPF_LEVEL_DIALOG_TAG = "chooseSPFLevel";

  private ChooseSPFLevelCaller caller;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      caller = (ChooseSPFLevelCaller) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement "
          + ChooseSPFLevelCaller.class.getSimpleName());
    }
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    GridView gridView = (GridView) getActivity()
        .getLayoutInflater().inflate(R.layout.choose_spf_level, null);

    final List<String> iconValues = SPFLevelIconUtils.getAllIconValues();
    List<Integer> imageIds = new ArrayList<Integer>();
    for (String iconValue : iconValues) {
      imageIds.add(SPFLevelIconUtils.getIconDrawable(iconValue));
    }

    ChooseSPFLevelImageAdapter imageAdapter = new ChooseSPFLevelImageAdapter(
        getActivity(), imageIds);
    gridView.setAdapter(imageAdapter);
    gridView.setOnItemClickListener(new OnItemClickListener() {
        @Override
      public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        dismiss();
        caller.onChooseSPFLevelDone(iconValues.get(position));
      }
    });

    return new AlertDialog.Builder(getActivity()).setNegativeButton(R.string.generic_cancel, null)
        .setTitle(R.string.uvg_spf_level_hint).setView(gridView).create();
  }
}