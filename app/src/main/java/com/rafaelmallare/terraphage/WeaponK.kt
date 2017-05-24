package com.rafaelmallare.terraphage

/**
 * Created by Rj on 5/23/2017.
 */
class WeaponK(name: String, silValue: Int, val type: WeaponTypeK, modifiers: Map<AttributeTypeK, Int> = mutableMapOf<AttributeTypeK, Int>(),
              var rank: WeaponRankK = WeaponRankK.E, var range: Int = 0, var maxAmmo: Int = 0, var AOE: Int = 0)
    : GearK(name, silValue, modifiers){

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