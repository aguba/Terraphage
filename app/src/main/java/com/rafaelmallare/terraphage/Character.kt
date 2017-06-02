package com.rafaelmallare.terraphage

import com.rafaelmallare.terraphage.AttributeType.*
import com.rafaelmallare.terraphage.StatType.*
import com.rafaelmallare.terraphage.WeaponType.*
import com.rafaelmallare.terraphage.GearType.*
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

/**
 * Created by Rj on 5/23/2017.
 */
object Character {
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

    val skills = Skills

    val attributes = listOf(Attribute(HP), Attribute(REGEN), Attribute(INIT), Attribute(SPD),
                            Attribute(DEF), Attribute(ATK), Attribute(MDMG), Attribute(RDMG),
                            Attribute(ARM))

    private var maxHealth = getAttribute(HP)
    var currentHealth = maxHealth
        set(value) {
            if (value > maxHealth) field = maxHealth
            else if (value < 0) field = 0
            else field = value
        }

    val unarmed = Gear("Unarmed", Weapon, Melee, 0)
    val unarmored = Gear("Unarmored", Armor, null, 0)
    var equippedWeapon = unarmed
    var equippedArmor = unarmored
    val inventory = mutableListOf<Gear>()
    val weaponInv: List<Gear>
        get() = inventory.filter { gear -> gear.type == Weapon }
    val armorInv: List<Gear>
        get() = inventory.filter { gear -> gear.type == Armor }
    val gadgetInv: List<Gear>
        get() = inventory.filter { gear -> gear.type == Gadget }

    fun getStat(stat: StatType) : Int = if (stat == ANI) anima else if (physStats.contains(stat)) physStats.get(stat) else mentStats.get(stat)

    fun increaseStatBy(stat: StatType, value: Int) {
        if (physStats.contains(stat)) physStats.increaseStatBy(stat, value)
        else if (mentStats.contains(stat)) mentStats.increaseStatBy(stat, value)
        else anima += value
    }

    fun getAttribute(attribute: AttributeType) = attributes[attribute.ordinal].totalValue

    fun equipWeapon(weapon: Gear) {
        if (equippedWeapon != unarmed) unequipWeapon(equippedWeapon)
        if (weapon in weaponInv) equippedWeapon = weapon
        for (attribute in attributes) attribute.addModifier(weapon)
    }

    fun unequipWeapon(weapon: Gear) {
        if (weapon == equippedWeapon) equippedWeapon = unarmed
        for (attribute in attributes) attribute.removeModifier(weapon)
    }

    fun equipArmor(armor: Gear) {
        if (equippedArmor != unarmored) unequipArmor(equippedArmor)
        if (armor in armorInv) equippedArmor = armor
        for (attribute in attributes) attribute.addModifier(armor)
    }

    fun unequipArmor(armor: Gear) {
        if (armor == equippedArmor) equippedArmor = unarmored
        for (attribute in attributes) attribute.removeModifier(armor)
    }

    fun increaseSkill(skill: String) : Boolean {
        if ((expCurrent - 5) >= 0) {
            skills[skill] = skills[skill] + 1
            expCurrent -= 5
            return true
        } else {
            return false
        }
    }

    class Attribute(val type: AttributeType) {
        val modifiers = mutableMapOf<String, Int>()

        val baseValue: Int
            get() {
                val value: Int
                when (type) {
                    AttributeType.HP -> {
                        value = getStat(CON) + physStats.get(STR) + 10
                        maxHealth = value
                        currentHealth = maxHealth
                    }
                    REGEN -> value = getStat(CON) / 2
                    INIT -> value = getStat(DEX) + mentStats.get(PER)
                    SPD -> value = (getStat(DEX) / 2) + 3
                    DEF -> value = getStat(DEX) + skills["Evasion", true].toInt()
                    ATK -> value = getStat(PER) + skills["Weapon Mastery", true].toInt()
                    MDMG -> value = getStat(STR)
                    RDMG -> value = if (equippedWeapon.subType == Ranged) (equippedWeapon.modifiers[RDMG] ?: 0) else 0
                    ARM -> value = (equippedArmor.modifiers[ARM] ?: 0) + (equippedWeapon.modifiers[ARM] ?: 0)
                    else -> value = 0
                }

                return value
            }

        val totalValue: Int
            get() {
                var value = baseValue

                if (type != RDMG && type != ARM) {
                    for (item in modifiers.values) {
                        value += item
                    }
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