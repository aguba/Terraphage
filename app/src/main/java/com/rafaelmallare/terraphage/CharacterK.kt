package com.rafaelmallare.terraphage

/**
 * Created by Rj on 5/23/2017.
 */
object CharacterK {
    var name = "Default Name"
    var homeland = "Default Homeland"
    var sil = 0
    var exp = 0

    var anima = 0

    object physStats : StatGroup(StatTypeK.CON, StatTypeK.STR, StatTypeK.DEX)
    object mentStats : StatGroup(StatTypeK.PER, StatTypeK.CHR, StatTypeK.INT)

    var attributes = mutableListOf(Attribute(AttributeTypeK.HP), Attribute(AttributeTypeK.REGEN),
                                            Attribute(AttributeTypeK.INIT), Attribute(AttributeTypeK.SPD),
                                            Attribute(AttributeTypeK.DEF), Attribute(AttributeTypeK.ATK),
                                            Attribute(AttributeTypeK.MDMG), Attribute(AttributeTypeK.RDMG),
                                            Attribute(AttributeTypeK.ARM))

    val unarmed = WeaponK("Unarmed", 0, WeaponTypeK.Melee)
    var equippedWeapon = unarmed
    var inventory = listOf<GearK>()

    fun getStat(stat: StatTypeK) : Int {
        if (physStats.contains(stat)) return physStats.get(stat) else return mentStats.get(stat)
    }

    fun increaseStatBy(stat: StatTypeK, value: Int) {
        if (physStats.contains(stat)) physStats.increaseStatBy(stat, value) else mentStats.increaseStatBy(stat, value)
    }

    class Attribute(val type: AttributeTypeK) {
        var modifiers = mutableMapOf<String, Int>()

        val baseValue: Int
            get() {
                val value: Int
                when (type) {
                    AttributeTypeK.HP -> value = physStats.get(StatTypeK.CON) + physStats.get(StatTypeK.STR) + 10
                    AttributeTypeK.REGEN -> value = physStats.get(StatTypeK.CON) / 2
                    AttributeTypeK.INIT -> value = physStats.get(StatTypeK.DEX) + mentStats.get(StatTypeK.PER)
                    AttributeTypeK.SPD -> value = (physStats.get(StatTypeK.DEX) / 2) + 3
                    AttributeTypeK.DEF -> value = mentStats.get(StatTypeK.DEX) /* + Evasion skill */
                    AttributeTypeK.ATK -> value = mentStats.get(StatTypeK.PER) /* + Weapon skill */
                    AttributeTypeK.RDMG -> value = 0 /* return ranged weapon damage */
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
        var statMap = mutableMapOf(Pair(statOne, 0), Pair(statTwo, 0), Pair(statThree, 0))

        private fun withinConstraint(stat: StatTypeK, value: Int): Boolean{
            var tmpMap = statMap.toMutableMap()
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