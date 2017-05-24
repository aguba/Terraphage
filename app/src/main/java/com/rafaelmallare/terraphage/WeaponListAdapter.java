package com.rafaelmallare.terraphage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

import static com.rafaelmallare.terraphage.AttributeType.ATK;
import static com.rafaelmallare.terraphage.AttributeType.MDMG;
import static com.rafaelmallare.terraphage.AttributeType.RDMG;

/**
 * Created by Rj on 3/18/2017.
 */

public class WeaponListAdapter extends ArrayAdapter<Weapon> {
    public WeaponListAdapter(Context context, ArrayList<Weapon> weapons){
        super(context, 0, weapons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Weapon weapon = getItem(position);
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_weapon, parent, false);
        }

        TextView weaponName = ButterKnife.findById(view, R.id.weapon_name);
        TextView equipped = ButterKnife.findById(view, R.id.is_equipped);
        TextView type = ButterKnife.findById(view, R.id.weapon_type);
        TextView silValue = ButterKnife.findById(view, R.id.sil_value);

        LinearLayout cellAtk = ButterKnife.findById(view, R.id.cell_atk);
        LinearLayout cellRdmg = ButterKnife.findById(view, R.id.cell_rdmg);
        LinearLayout cellMdmg = ButterKnife.findById(view, R.id.cell_mdmg);
        LinearLayout cellAOE = ButterKnife.findById(view, R.id.cell_aoe);
        LinearLayout cellAmmo = ButterKnife.findById(view, R.id.cell_ammo);
        LinearLayout cellRange = ButterKnife.findById(view, R.id.cell_range);

        TextView valAtk = ButterKnife.findById(view, R.id.value_atk);
        TextView valRdmg = ButterKnife.findById(view, R.id.value_rdmg);
        TextView valMdmg = ButterKnife.findById(view, R.id.value_mdmg);
        TextView valAOE = ButterKnife.findById(view, R.id.value_aoe);
        TextView valAmmoCurrent = ButterKnife.findById(view, R.id.value_ammo_current);
        TextView valAmmoTotal = ButterKnife.findById(view, R.id.value_ammo_total);
        TextView valRange = ButterKnife.findById(view, R.id.value_range);

        weaponName.setText(weapon.getName());
        type.setText(weapon.getWeaponType().toString());
        silValue.setText("" + weapon.getSilValue());

        if (weapon.isEquipped()){
            equipped.setVisibility(View.VISIBLE);
        }

        if (weapon.getModifier(ATK) != 0){
            valAtk.setText("" + weapon.getModifier(ATK));
        } else {
            cellAtk.setVisibility(View.GONE);
        }

        if (weapon.getModifier(MDMG) != 0){
            valMdmg.setText("" + weapon.getModifier(MDMG));
        } else {
            cellMdmg.setVisibility(View.GONE);
        }

        if (weapon.getModifier(RDMG) != 0){
            valRdmg.setText("" + weapon.getModifier(RDMG));
        } else {
            cellRdmg.setVisibility(View.GONE);
        }

        if (weapon.getAOE() != 0){
            valAOE.setText("" + weapon.getAOE());
        } else {
            cellAOE.setVisibility(View.GONE);
        }

        if (weapon.getRange() != 0){
            valRange.setText("" + weapon.getRange());
        } else {
            cellRange.setVisibility(View.GONE);
        }

        if (weapon.getAmmoMax() != 0){
            valAmmoTotal.setText("" + weapon.getAmmoMax());
            valAmmoCurrent.setText("" + weapon.getAmmoCurrent());
        } else {
            cellAmmo.setVisibility(View.GONE);
        }

        return view;
    }
}
