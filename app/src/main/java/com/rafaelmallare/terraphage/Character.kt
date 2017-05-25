package com.rafaelmallare.terraphage

import com.rafaelmallare.terraphage.AttributeType.*
import com.rafaelmallare.terraphage.StatType.*
import com.rafaelmallare.terraphage.WeaponRank.*
import com.rafaelmallare.terraphage.WeaponType.*

/**
 * Created by Rj on 5/23/2017.
 */
class Character {
    var name = "Default Name"
    var homeland = "Default Homeland"
    var sil = 0

    var expTotal = 0
        set(value) {
            if (value > field) {
                expCurrent += value - field
                field = value
            }
        }

    var expCurrent = expTotal

    var anima = 0

    object physStats : StatGroup(CON, STR, DEX)
    object mentStats : StatGroup(PER, CHR, INT)

    val attributes = listOf(Attribute(HP), Attribute(REGEN), Attribute(INIT), Attribute(SPD),
                            Attribute(DEF), Attribute(ATK), Attribute(MDMG), Attribute(RDMG),
                            Attribute(ARM))

    var currentHealth = getAttribute(HP)
        set(value) {
            if (value > getAttribute(HP)) field = getAttribute(HP)
            else if (value < 0) field = 0
            else field = value
        }

    val unarmed = Weapon("Unarmed", 0, Melee)
    var equippedWeapon = unarmed
    val inventory = listOf<Gear>()

    fun getStat(stat: StatType) : Int {
        if (physStats.contains(stat)) return physStats.get(stat) else return mentStats.get(stat)
    }

    fun increaseStatBy(stat: StatType, value: Int) {
        if (physStats.contains(stat)) physStats.increaseStatBy(stat, value) else mentStats.increaseStatBy(stat, value)
    }

    fun getAttribute(attribute: AttributeType) : Int {
        return attributes[attribute.ordinal].totalValue
    }

    fun equipWeapon(weapon: Weapon) {
        if (weapon in inventory) equippedWeapon = weapon
    }

    fun unequipWeapon(weapon: Weapon) {
        if (weapon == equippedWeapon) equippedWeapon = unarmed
    }

    inner class Attribute(val type: AttributeType) {
        val modifiers = mutableMapOf<String, Int>()

        val baseValue: Int
            get() {
                val value: Int
                when (type) {
                    AttributeType.HP -> value = getStat(CON) + physStats.get(STR) + 10
                    AttributeType.REGEN -> value = getStat(CON) / 2
                    AttributeType.INIT -> value = getStat(DEX) + mentStats.get(PER)
                    AttributeType.SPD -> value = (getStat(DEX) / 2) + 3
                    AttributeType.DEF -> value = getStat(DEX) /* + Evasion skill */
                    AttributeType.ATK -> value = getStat(PER) /* + Weapon skill */
                    AttributeType.MDMG -> value = getStat(STR)
                    AttributeType.RDMG -> value = if (equippedWeapon.type == Ranged) equippedWeapon.modifiers[RDMG] ?: -1 else 0
                    AttributeType.ARM -> value = 0 /* return ARM of gear */
                    else -> value = 0
                }

                return value
            }

        val totalValue: Int
            get() {
                var value = baseValue
                for (item in modifiers.values){
                    value += item
                }

                return value
            }

        fun addModifier(gear: Gear) {
            if (gear.modifiers.containsKey(type)) modifiers.put(gear.name, gear.modifiers[type] ?: 0)
        }

        fun removeModifier(gear: Gear) {
            if (modifiers.containsKey(gear.name)) modifiers.remove(gear.name)
        }
    }

    open class StatGroup(statOne: StatType, statTwo: StatType, statThree: StatType) {
        val statMap = mutableMapOf(Pair(statOne, 0), Pair(statTwo, 0), Pair(statThree, 0))

        private fun withinConstraint(stat: StatType, value: Int): Boolean{
            val tmpMap = statMap.toMutableMap()
            tmpMap.put(stat, (tmpMap[stat] ?: 0) + value)

            return ((tmpMap.values.max() ?: 0) - (tmpMap.values.min() ?: 0)) <= 4
        }

        fun increaseStatBy(stat: StatType, value: Int) {
            if (withinConstraint(stat, value)) {
                statMap.put(stat, (statMap[stat] ?: 0) + value)
            }
        }

        fun get(stat: StatType) : Int {
            return statMap[stat] ?: -1
        }

        fun contains(stat: StatType) : Boolean {
            return statMap.contains(stat)
        }
    }
}