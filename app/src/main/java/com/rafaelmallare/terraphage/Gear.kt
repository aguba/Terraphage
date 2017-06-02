package com.rafaelmallare.terraphage

import com.rafaelmallare.terraphage.GearType.*

/**
 * Created by Rj on 5/23/2017.
 */
open class Gear(val name: String, val type: GearType, val subType: WeaponType? = null,
                val silValue: Int, var modifiers: Map<AttributeType, Int> = mutableMapOf<AttributeType, Int>(),
                var range: Int = 0, var maxAmmo: Int = 0, var AOE: Int = 0, var rank: WeaponRank? = WeaponRank.E){
    val refId: Int

    private companion object{
        var id = 0
    }

    init{
        id++
        refId = id

        if (type == Weapon) {
            rank = null
        }
    }

    var currentAmmo: Int = maxAmmo
        get() = field
        set(value) {
            if (value > maxAmmo){
                field = maxAmmo
            } else if (value < 0){
                field = 0
            } else{
                field = value
            }
        }
}