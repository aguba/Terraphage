package com.rafaelmallare.terraphage;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.id;
import static com.rafaelmallare.terraphage.AttributeType.ATK;
import static com.rafaelmallare.terraphage.AttributeType.DEF;
import static com.rafaelmallare.terraphage.AttributeType.HP;
import static com.rafaelmallare.terraphage.AttributeType.INIT;
import static com.rafaelmallare.terraphage.AttributeType.MDMG;
import static com.rafaelmallare.terraphage.AttributeType.RDMG;
import static com.rafaelmallare.terraphage.AttributeType.SPD;
import static com.rafaelmallare.terraphage.StatType.CHR;
import static com.rafaelmallare.terraphage.StatType.CON;
import static com.rafaelmallare.terraphage.StatType.DEX;
import static com.rafaelmallare.terraphage.StatType.INT;
import static com.rafaelmallare.terraphage.StatType.PER;
import static com.rafaelmallare.terraphage.StatType.STR;
import static com.rafaelmallare.terraphage.Weapon.Rank.D;
import static com.rafaelmallare.terraphage.WeaponType.Melee;
import static com.rafaelmallare.terraphage.WeaponType.Ranged;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();

    static final int REQUEST_CODE_PICKER = 100;

    float scale;

    //region -REGION: View Bindings-
    @BindView(R.id.content_main) RelativeLayout rootView;

    @BindView(R.id.character_name) TextView characterName_view;
    @BindView(R.id.character_path) TextView characterPath_view;

    @BindView(R.id.hp_current_val) TextView hpCurrentVal_view;
    @BindView(R.id.hp_total_val) TextView hpTotalVal_view;
    @BindView(R.id.exp_current_val) TextView expCurrentVal_view;
    @BindView(R.id.exp_total_val) TextView expTotalVal_view;

    @BindView(R.id.init_val) TextView initVal_view;
    @BindView(R.id.spd_val) TextView spdVal_view;
    @BindView(R.id.dmg_val) TextView dmgVal_view;
    @BindView(R.id.def_val) TextView defVal_view;

    @BindView(R.id.per_val) TextView perVal_view;
    @BindView(R.id.chr_val) TextView chrVal_view;
    @BindView(R.id.int_val) TextView intVal_view;
    @BindView(R.id.con_val) TextView conVal_view;
    @BindView(R.id.str_val) TextView strVal_view;
    @BindView(R.id.dex_val) TextView dexVal_view;
    @BindView(R.id.header_image) ImageView headerImage_view;

    @BindView(R.id.atk_card) CardView atkCardView;
    @BindView(R.id.atk_expandable) LinearLayout atkExpandable_view;
    @BindView(R.id.btn_atk_expand) ImageButton btnAtkExpand;
    @BindView(R.id.list_weapons) ListView weaponListView;

    @BindView(R.id.def_card) CardView defCardView;
    @BindView(R.id.def_expandable) LinearLayout defExpandable_view;
    @BindView(R.id.btn_def_expand) ImageButton btnDefExpand;
    //endregion

    final Character character = Character.getInstance();
    final GearStore gearStore = GearStore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region -REGION: Initialization stuff (includes FAB code)-
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        scale = getResources().getDisplayMetrics().density;

        /***Animation stuff**/
        //LayoutTransition layoutTransition = rootView.getLayoutTransition();
        //layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(this)
                .build();

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(imageLoaderConfig);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //endregion

        character.addGear(new Weapon("Revolver", 200, Ranged, ATK, -1, RDMG, 10));
        character.addGear(new Weapon("Great Sword", 15, Melee, ATK, -3, MDMG, 4));
        character.addGear(new Weapon("Flail", 10, Melee, ATK, -3, MDMG, 4));

        updateViewValues();
    }

    public void updateViewValues(){
        characterName_view.setText(character.getName());

        hpTotalVal_view.setText("" + character.getAttribute(HP).getTotalValue());
        hpCurrentVal_view.setText(hpTotalVal_view.getText());

        initVal_view.setText("" + character.getAttribute(INIT).getTotalValue());
        spdVal_view.setText("" + character.getAttribute(SPD).getTotalValue());
        dmgVal_view.setText("" + character.getAttribute(MDMG).getTotalValue());
        defVal_view.setText("" + character.getAttribute(DEF).getTotalValue());

        expTotalVal_view.setText("" + character.getExp());
        expCurrentVal_view.setText(expTotalVal_view.getText());

        perVal_view.setText("" + character.getStat(PER));
        chrVal_view.setText("" + character.getStat(CHR));
        intVal_view.setText("" + character.getStat(INT));
        conVal_view.setText("" + character.getStat(CON));
        strVal_view.setText("" + character.getStat(STR));
        dexVal_view.setText("" + character.getStat(DEX));
    }

    /******test methods*******/
    @OnClick(R.id.dex_val)
    public void increaseDex(){
        character.increaseStat(DEX, 1);
        updateViewValues();
    }

    @OnClick(R.id.str_val)
    public void increaseStr(){
        character.increaseStat(STR, 1);
        updateViewValues();
    }

    @OnClick(R.id.con_val)
    public void increaseCon(){
        character.increaseStat(CON, 1);
        updateViewValues();
    }
    /************************/

    @OnClick({R.id.btn_atk_expand, R.id.btn_def_expand})
    public void expand(ImageButton imgBtn){
        int currentVisibility;
        int newVisibility;
        int pad;
        int id = imgBtn.getId();

        CardView cardView = null;
        LinearLayout expandView = null;
        int backgroundColor;
        int tintColor;
        int expandArrow;

        WeaponListAdapter weaponListAdapter = new WeaponListAdapter(this, character.getWeapons());
        weaponListView.setAdapter(weaponListAdapter);

        if(id == btnAtkExpand.getId()){
            cardView = atkCardView;
            expandView = atkExpandable_view;
        } else if(id == btnDefExpand.getId()){
            cardView = defCardView;
            expandView = defExpandable_view;
        }

        currentVisibility = expandView.getVisibility();

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)cardView.getLayoutParams();

        if (currentVisibility == View.GONE){
            params.weight = 1000;
            newVisibility = View.VISIBLE;
            pad = toDP(35);
            backgroundColor = R.color.clear;
            tintColor = R.color.colorExpandableTintDark;
            expandArrow = R.drawable.expand_up;
        } else{
            params.weight = 1;
            newVisibility = View.GONE;
            pad = toDP(15);
            backgroundColor = R.color.colorExpandableBackgroundDark;
            tintColor = R.color.colorExpandableTintLight;
            expandArrow = R.drawable.expand_down;
        }

        imgBtn.setPadding(pad, pad, pad, pad);
        imgBtn.setBackgroundColor(getResources().getColor(backgroundColor));
        imgBtn.setColorFilter(getResources().getColor(tintColor));
        imgBtn.setImageDrawable(getResources().getDrawable(expandArrow));
        cardView.setLayoutParams(params);
        expandView.setVisibility(newVisibility);
    }

    public int toDP(int num){
        return (int) (num * scale + 0.5f);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_change_header_image){
            Intent headerImagePicker = new Intent(Intent.ACTION_PICK);
            headerImagePicker.setType("image/*");
            this.startActivityForResult(headerImagePicker, REQUEST_CODE_PICKER);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            imageLoader.displayImage(data.getData().toString(), headerImage_view);
        }
    }
}
