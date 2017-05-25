package com.rafaelmallare.terraphage

/**
 * Created by Rj on 5/23/2017.
 */
open class Gear(val name: String, val silValue: Int, var modifiers: Map<AttributeType, Int>) {
    val refId: Int

    private companion object{
        var id = 0
    }

    init{
        id++
        refId = id
    }
}