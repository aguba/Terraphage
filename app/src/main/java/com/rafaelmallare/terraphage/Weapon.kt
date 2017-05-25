package com.rafaelmallare.terraphage

/**
 * Created by Rj on 5/23/2017.
 */
class Weapon(name: String, silValue: Int, val type: WeaponType, modifiers: Map<AttributeType, Int> = mutableMapOf<AttributeType, Int>(),
             var rank: WeaponRank = WeaponRank.E, var range: Int = 0, var maxAmmo: Int = 0, var AOE: Int = 0)
    : Gear(name, silValue, modifiers){

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