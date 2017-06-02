package com.rafaelmallare.terraphage

/**
 * Created by Rj on 5/30/2017.
 */
object Skills {
    private val skills = mutableMapOf(
            "Anima Mastery" to 1.0,
            "Gargoyle Grappling" to 1.0,
            "Evasion" to 1.0,
            "Block" to 1.0,
            "Weapon Mastery" to 1.0,
            "Skinning" to 1.0,
            "Engineering" to 1.0,
            "Stealth" to 1.0,
            "Detection" to 1.0,
            "Poisoncraft" to 1.0,
            "Blacksmithing" to 1.0,
            "Bow Crafting" to 1.0,
            "Deception" to 1.0,
            "Intent" to 1.0,
            "Disguise" to 1.0,
            "Lore" to 1.0,
            "Etiquette" to 1.0,
            "Negotiation" to 1.0,
            "Intimidation" to 1.0,
            "Appraisal" to 1.0,
            "Medicine" to 1.0,
            "Bribery" to 1.0,
            "Tracking" to 1.0,
            "Gambling" to 1.0,
            "Riding" to 1.0,
            "Rope Use" to 1.0,
            "Research" to 1.0,
            "Thievery" to 1.0,
            "Acrobatics" to 1.0,
            "Performance" to 1.0,
            "Trapping" to 1.0
    )

    operator fun get(skill: String, level: Boolean = false) : Double {
        var value = skills[skill] ?: 0.0
        if (level) value = Math.floor(value)
        return value
    }
    operator fun set(skill: String, value: Double) {
        skills[skill] = value
    }
}