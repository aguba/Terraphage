package com.rafaelmallare.terraphage

/**
 * Created by Rj on 5/24/2017.
 */
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import com.rafaelmallare.terraphage.AttributeType.*
import com.rafaelmallare.terraphage.StatType.*
import com.rafaelmallare.terraphage.WeaponType.*
import com.rafaelmallare.terraphage.GearType.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val character = Character
    var density = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        density = resources.displayMetrics.density

        character.inventory.add(Gear("Great Sword", Weapon, Melee, 15, mapOf(ATK to -3, MDMG to 4)))
        character.inventory.add(Gear("Shield", Weapon, Melee, 8, mapOf(ARM to 1)))
        character.inventory.add(Gear("Revolver", Weapon, Ranged, 200, mapOf(ATK to -1, RDMG to 10), 10, 6))
        character.inventory.add(Gear("Leather Armor", Armor, null, 10, mapOf(ARM to 2)))
        character.inventory.add(Gear("Combat Armor", Armor, null, 450, mapOf(ARM to 6, DEF to -1)))
        character.inventory.add(Gear("Dragon Skin Armor", Armor, null, 850, mapOf(ARM to 9, DEF to -3)))

        updateViewValues()

        setOnClickListeners(listOf(btn_atk_expand, btn_def_expand), {
            view ->
                val imgBtn = view as ImageButton
                val expandView: LinearLayout
                val cardView = if (imgBtn.id == btn_atk_expand.id) {
                    list_weapons.adapter = GearListAdapter(this, character.weaponInv, { updateViewValues() })
                    expandView = atk_expandable
                    atk_card
                } else {
                    list_armor.adapter = GearListAdapter(this, character.armorInv, { updateViewValues() })
                    expandView = def_expandable
                    def_card
                }

                val params = cardView.layoutParams as LinearLayout.LayoutParams

                if (expandView.visibility == View.GONE) {
                    params.weight = 1000f
                    imgBtn.setPadding(35.toDP(), 35.toDP(), 35.toDP(), 35.toDP())
                    imgBtn.setBackgroundColor(resources.getColor(R.color.clear))
                    imgBtn.setColorFilter(resources.getColor(R.color.colorExpandableTintDark))
                    imgBtn.setImageDrawable(resources.getDrawable(R.drawable.expand_up))
                    expandView.visibility = View.VISIBLE
                } else {
                    params.weight = 1f
                    imgBtn.setPadding(15.toDP(), 15.toDP(), 15.toDP(), 15.toDP())
                    imgBtn.setBackgroundColor(resources.getColor(R.color.colorExpandableBackgroundDark))
                    imgBtn.setColorFilter(resources.getColor(R.color.colorExpandableTintLight))
                    imgBtn.setImageDrawable(resources.getDrawable(R.drawable.expand_down))
                    expandView.visibility = View.GONE
                }

        })

        //-------------------------------------Test Functions----------------------------------------------
        setOnClickListeners(listOf(per_val, chr_val, int_val, con_val, str_val, dex_val), {
            view -> kotlin.run {
                when(view.id) {
                    per_val.id -> character.increaseStatBy(PER, 1)
                    chr_val.id -> character.increaseStatBy(CHR, 1)
                    int_val.id -> character.increaseStatBy(INT, 1)
                    con_val.id -> character.increaseStatBy(CON, 1)
                    str_val.id -> character.increaseStatBy(STR, 1)
                    dex_val.id -> character.increaseStatBy(DEX, 1)
                }
                updateViewValues()
            }
        })
        //-------------------------------------------------------------------------------------------------
    }

    fun updateViewValues() {
        character_name.text = character.name

        hp_total_val.text = character.getAttribute(HP).toString()
        hp_current_val.text = character.currentHealth.toString()

        init_val.text = character.getAttribute(INIT).toString()
        spd_val.text = character.getAttribute(SPD).toString()
        def_val.text = character.getAttribute(DEF).toString()
        dmg_val.text = if (character.equippedWeapon.subType == Melee) character.getAttribute(MDMG).toString()
                       else character.getAttribute(RDMG).toString()
        arm_val.text = character.getAttribute(ARM).toString()
        atk_val.text = character.getAttribute(ATK).toString()

        exp_total_val.text = character.expTotal.toString()
        exp_current_val.text = character.expCurrent.toString()

        per_val.text = character.getStat(PER).toString()
        chr_val.text = character.getStat(CHR).toString()
        int_val.text = character.getStat(INT).toString()
        con_val.text = character.getStat(CON).toString()
        str_val.text = character.getStat(STR).toString()
        dex_val.text = character.getStat(DEX).toString()
    }

    fun Int.toDP() : Int {
        return (this * density + 0.5).toInt()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}