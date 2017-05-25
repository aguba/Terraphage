package com.rafaelmallare.terraphage

/**
 * Created by Rj on 5/23/2017.
 */
class CharacterK {
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

    object physStats : StatGroup(StatTypeK.CON, StatTypeK.STR, StatTypeK.DEX)
    object mentStats : StatGroup(StatTypeK.PER, StatTypeK.CHR, StatTypeK.INT)

    val attributes = listOf(Attribute(AttributeTypeK.HP), Attribute(AttributeTypeK.REGEN),
                                            Attribute(AttributeTypeK.INIT), Attribute(AttributeTypeK.SPD),
                                            Attribute(AttributeTypeK.DEF), Attribute(AttributeTypeK.ATK),
                                            Attribute(AttributeTypeK.MDMG), Attribute(AttributeTypeK.RDMG),
                                            Attribute(AttributeTypeK.ARM))

    var currentHealth = getAttribute(AttributeTypeK.HP)
        set(value) {
            if (value > getAttribute(AttributeTypeK.HP)) field = getAttribute(AttributeTypeK.HP)
            else if (value < 0) field = 0
            else field = value
        }

    val unarmed = WeaponK("Unarmed", 0, WeaponTypeK.Melee)
    var equippedWeapon = unarmed
    val inventory = listOf<GearK>()

    fun getStat(stat: StatTypeK) : Int {
        if (physStats.contains(stat)) return physStats.get(stat) else return mentStats.get(stat)
    }

    fun increaseStatBy(stat: StatTypeK, value: Int) {
        if (physStats.contains(stat)) physStats.increaseStatBy(stat, value) else mentStats.increaseStatBy(stat, value)
    }

    fun getAttribute(attribute: AttributeTypeK) : Int {
        return attributes[attribute.ordinal].totalValue
    }

    fun equipWeapon(weapon: WeaponK) {
        if (weapon in inventory) equippedWeapon = weapon
    }

    fun unequipWeapon(weapon: WeaponK) {
        if (weapon == equippedWeapon) equippedWeapon = unarmed
    }

    inner class Attribute(val type: AttributeTypeK) {
        val modifiers = mutableMapOf<String, Int>()

        val baseValue: Int
            get() {
                val value: Int
                when (type) {
                    AttributeTypeK.HP -> value = getStat(StatTypeK.CON) + physStats.get(StatTypeK.STR) + 10
                    AttributeTypeK.REGEN -> value = getStat(StatTypeK.CON) / 2
                    AttributeTypeK.INIT -> value = getStat(StatTypeK.DEX) + mentStats.get(StatTypeK.PER)
                    AttributeTypeK.SPD -> value = (getStat(StatTypeK.DEX) / 2) + 3
                    AttributeTypeK.DEF -> value = getStat(StatTypeK.DEX) /* + Evasion skill */
                    AttributeTypeK.ATK -> value = getStat(StatTypeK.PER) /* + Weapon skill */
                    AttributeTypeK.MDMG -> value = getStat(StatTypeK.STR)
                    AttributeTypeK.RDMG -> value = if (equippedWeapon.type == WeaponTypeK.Ranged) equippedWeapon.modifiers[AttributeTypeK.RDMG] ?: -1 else 0
                    AttributeTypeK.ARM -> value = 0 /* return ARM of gear */
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

        fun addModifier(gear: GearK) {
            if (gear.modifiers.containsKey(type)) modifiers.put(gear.name, gear.modifiers[type] ?: 0)
        }

        fun removeModifier(gear: GearK) {
            if (modifiers.containsKey(gear.name)) modifiers.remove(gear.name)
        }
    }

    open class StatGroup(statOne: StatTypeK, statTwo: StatTypeK, statThree: StatTypeK) {
        val statMap = mutableMapOf(Pair(statOne, 0), Pair(statTwo, 0), Pair(statThree, 0))

        private fun withinConstraint(stat: StatTypeK, value: Int): Boolean{
            val tmpMap = statMap.toMutableMap()
            tmpMap.put(stat, (tmpMap[stat] ?: 0) + value)

            return ((tmpMap.values.max() ?: 0) - (tmpMap.values.min() ?: 0)) <= 4
        }

        fun increaseStatBy(stat: StatTypeK, value: Int) {
            if (withinConstraint(stat, value)) {
                statMap.put(stat, (statMap[stat] ?: 0) + value)
            }
        }

        fun get(stat: StatTypeK) : Int {
            return statMap[stat] ?: -1
        }

        fun contains(stat: StatTypeK) : Boolean {
            return statMap.contains(stat)
        }
    }
}