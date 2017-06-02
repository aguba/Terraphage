package com.rafaelmallare.terraphage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rafaelmallare.terraphage.AttributeType.*
import com.rafaelmallare.terraphage.GearType.*
import kotlinx.android.synthetic.main.item_gear.view.*

/**
 * Created by Rj on 5/25/2017.
 */
class GearListAdapter(context: Context, gearList: List<Gear>, val updateFunction: () -> Unit = {})
    : ArrayAdapter<Gear>(context, 0, gearList) {

    val character = Character

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gear = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_gear, parent, false)

        val attrTable = listOf<Pair<Int?, Pair<LinearLayout, TextView>>>(
                gear.modifiers[ATK] to Pair(view.cell_atk, view.value_atk),
                gear.modifiers[MDMG] to Pair(view.cell_mdmg, view.value_mdmg),
                gear.modifiers[RDMG] to Pair(view.cell_rdmg, view.value_rdmg),
                gear.modifiers[ARM] to Pair(view.cell_arm, view.value_arm),
                gear.maxAmmo to Pair(view.cell_ammo, view.value_ammo_total),
                gear.range to Pair(view.cell_range, view.value_range),
                gear.AOE to Pair(view.cell_aoe, view.value_aoe)
        )

        view.gear_name.text = gear.name
        view.gear_subtype.text = gear.type.toString()
        view.sil_value.text = gear.silValue.toString()

        for ((value, views) in attrTable) {
            if (value != null && value != 0) {
                views.second.text = value.toString()
                if (views.first.id == view.cell_ammo.id) view.value_ammo_current.text = gear.currentAmmo.toString()
            } else {
                views.first.visibility = View.GONE
            }
        }

        view.checkbox.isChecked = if (gear.type == Weapon) (character.equippedWeapon == gear) else (character.equippedArmor == gear)
        view.checkbox.setOnClickListener {
            if (view.checkbox.isChecked) {
                if (gear.type == Weapon) character.equipWeapon(gear) else character.equipArmor(gear)
            } else {
                if (gear.type == Weapon) character.unequipWeapon(gear) else character.unequipArmor(gear)
            }
            notifyDataSetChanged()
            updateFunction()
        }

        //return super.getView(position, convertView, parent)
        return view
    }
}