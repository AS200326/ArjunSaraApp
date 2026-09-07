package com.arjunsara.couplefun;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import androidx.core.content.FileProvider;
import java.io.*;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root, content;
    TextView title, subtitle;
    Random random = new Random();
    MediaPlayer music;
    CountDownTimer timer;
    boolean musicOn=true, soundOn=true, darkMode=true;
    String partner1="", partner2="", avatar1="👨🏻", avatar2="👩🏻";
    int quizIndex=0, quizScore=0, xp=0, coins=100, loveMeter=0;
    int roundNumber=1, roundScore=0, totalGameScore=0, p1Choice=-1;
    SharedPreferences data;

    String[] questions = makeQuestions();
    String[] truths = makeTruths();
    String[] dares = makeDares();
    String[] challenges = makeChallenges();
    String[] punishments = makePunishments();
    String[] messages = makeMessages();
    String[] birthday = makeBirthday();
    String[] anniversary = makeAnniversary();
    String[] rewards = {"Cute Couple Badge 🥰","Extra 20 Coins 🪙","Date Night Idea 💕","Love Champion 🏆","Sweet Surprise Unlocked 🎁","Heart Collector ❤️","Romance Master 💖","Forever Team ✨"};

    @Override public void onCreate(Bundle b){
        super.onCreate(b);
        data=getSharedPreferences("couple_data",MODE_PRIVATE);
        load();
        showSplash();
        if(musicOn) startMusic();
    }

    void load(){ partner1=data.getString("p1",""); partner2=data.getString("p2",""); avatar1=data.getString("avatar1","👨🏻"); avatar2=data.getString("avatar2","👩🏻"); xp=data.getInt("xp",0); coins=data.getInt("coins",100); loveMeter=data.getInt("meter",0); musicOn=data.getBoolean("music",true); soundOn=data.getBoolean("sound",true); darkMode=data.getBoolean("dark",true); }
    void save(){ data.edit().putString("p1",partner1).putString("p2",partner2).putString("avatar1",avatar1).putString("avatar2",avatar2).putInt("xp",xp).putInt("coins",coins).putInt("meter",loveMeter).putBoolean("music",musicOn).putBoolean("sound",soundOn).putBoolean("dark",darkMode).apply(); }
    void gain(int addXp,int addCoins){ xp+=addXp; coins+=addCoins; save(); }

    int dp(float v){ return (int)(v*getResources().getDisplayMetrics().density+0.5f); }
    int bgTop(){ return darkMode?Color.rgb(8,10,27):Color.rgb(255,247,251); }
    int bgBottom(){ return darkMode?Color.rgb(42,13,63):Color.rgb(255,220,235); }
    int textMain(){ return darkMode?Color.WHITE:Color.rgb(38,25,45); }
    int textSoft(){ return darkMode?Color.rgb(218,207,230):Color.rgb(105,76,96); }
    int pink(){ return Color.rgb(246,48,126); }
    int purple(){ return Color.rgb(137,55,190); }
    int gold(){ return Color.rgb(255,193,70); }

    TextView tv(String s,int size){
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(textMain());
        t.setGravity(Gravity.CENTER); t.setPadding(dp(14),dp(10),dp(14),dp(10));
        t.setLetterSpacing(size>=20?0.01f:0f); return t;
    }

    TextView sectionTitle(String s){
        TextView t=tv(s.toUpperCase(Locale.US),13); t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        t.setTextColor(darkMode?Color.rgb(255,214,232):Color.rgb(170,38,99));
        t.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL); t.setPadding(dp(10),dp(18),dp(10),dp(6));
        return t;
    }

    Button btn(String s){
        Button b=new Button(this); b.setText(s); b.setTextSize(15.5f); b.setTextColor(Color.WHITE);
        b.setAllCaps(false); b.setGravity(Gravity.CENTER_VERTICAL); b.setMinHeight(0); b.setMinimumHeight(0);
        b.setPadding(dp(20),dp(8),dp(20),dp(8)); b.setIncludeFontPadding(true);
        GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{Color.rgb(250,65,139),Color.rgb(137,45,186)});
        g.setCornerRadius(dp(22)); g.setStroke(dp(1),Color.argb(105,255,255,255)); b.setBackground(g);
        b.setElevation(dp(5));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(58)); lp.setMargins(dp(8),dp(6),dp(8),dp(6)); b.setLayoutParams(lp);
        animateButton(b); return b;
    }

    Button outlineBtn(String s){
        Button b=new Button(this); b.setText(s); b.setTextSize(14); b.setTextColor(darkMode?Color.WHITE:Color.rgb(119,38,91));
        b.setAllCaps(false); b.setGravity(Gravity.CENTER); b.setMinHeight(0); b.setMinimumHeight(0);
        GradientDrawable g=new GradientDrawable(); g.setColor(Color.TRANSPARENT); g.setCornerRadius(dp(20));
        g.setStroke(dp(1),darkMode?Color.rgb(230,116,176):Color.rgb(196,86,137)); b.setBackground(g);
        b.setElevation(dp(2)); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(50)); lp.setMargins(dp(8),dp(5),dp(8),dp(5)); b.setLayoutParams(lp);
        animateButton(b); return b;
    }

    Button compactTile(String icon,String label){
        Button b=new Button(this);
        b.setText(icon+"\n"+label);
        b.setTextSize(11.2f); b.setTextColor(Color.rgb(58,30,58)); b.setAllCaps(false);
        b.setGravity(Gravity.CENTER); b.setPadding(dp(3),dp(3),dp(3),dp(3));
        GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{Color.rgb(255,252,254),Color.rgb(255,224,239)});
        g.setCornerRadius(dp(16)); g.setStroke(dp(1),Color.rgb(239,112,174));
        b.setBackground(g); b.setElevation(dp(3));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,dp(78),1);
        lp.setMargins(dp(3),dp(4),dp(3),dp(4)); b.setLayoutParams(lp);
        animateButton(b); return b;
    }

    Button premiumTile(String icon,String label){
        Button b=new Button(this);
        b.setText(icon+"\n"+label);
        b.setTextSize(12.5f);
        b.setTextColor(Color.rgb(52,28,55));
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setPadding(dp(5),dp(5),dp(5),dp(5));
        GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{Color.rgb(255,248,252),Color.rgb(255,220,237)});
        g.setCornerRadius(dp(18));
        g.setStroke(dp(1),Color.rgb(244,121,177));
        b.setBackground(g);
        b.setElevation(dp(4));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,dp(86),1);
        lp.setMargins(dp(4),dp(4),dp(4),dp(4));
        b.setLayoutParams(lp);
        animateButton(b);
        return b;
    }

    TextView premiumCard(String text,int size){
        TextView v=tv(text,size);
        v.setTextColor(Color.rgb(47,26,52));
        v.setGravity(Gravity.CENTER);
        GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{Color.rgb(255,251,253),Color.rgb(255,225,239)});
        g.setCornerRadius(dp(22));
        g.setStroke(dp(1),Color.rgb(239,131,180));
        v.setBackground(g);
        v.setElevation(dp(3));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(145));
        lp.setMargins(dp(8),dp(8),dp(8),dp(8));
        v.setLayoutParams(lp);
        return v;
    }

    void base(String h,String sub){
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(8),dp(6),dp(8),0);
        GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{Color.rgb(7,8,24),Color.rgb(29,8,49),Color.rgb(8,15,34)}); root.setBackground(bg);
        ScrollView scroll=new ScrollView(this); scroll.setFillViewport(true); scroll.setClipToPadding(false);
        content=new LinearLayout(this); content.setOrientation(LinearLayout.VERTICAL); content.setGravity(Gravity.CENTER_HORIZONTAL); content.setPadding(dp(4),0,dp(4),dp(18));
        scroll.addView(content,new ScrollView.LayoutParams(-1,-2)); root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        LinearLayout brand=new LinearLayout(this); brand.setGravity(Gravity.CENTER_VERTICAL); brand.setPadding(dp(5),dp(3),dp(5),0);
        ImageView mini=new ImageView(this); mini.setImageResource(R.mipmap.ic_launcher); mini.setScaleType(ImageView.ScaleType.CENTER_CROP); GradientDrawable ring=new GradientDrawable(); ring.setShape(GradientDrawable.OVAL); ring.setStroke(dp(2),Color.rgb(255,82,158)); mini.setBackground(ring); brand.addView(mini,new LinearLayout.LayoutParams(dp(42),dp(42)));
        TextView bn=tv("Mogudu Pellam",17); bn.setTypeface(Typeface.DEFAULT,Typeface.BOLD); bn.setTextColor(Color.WHITE); bn.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL); brand.addView(bn,new LinearLayout.LayoutParams(0,dp(42),1));
        TextView bh=tv("♥",27); bh.setTextColor(Color.rgb(255,83,159)); brand.addView(bh,new LinearLayout.LayoutParams(dp(38),dp(42))); content.addView(brand,new LinearLayout.LayoutParams(-1,dp(46)));
        title=tv(h,24); title.setTypeface(Typeface.DEFAULT,Typeface.BOLD); title.setTextColor(Color.WHITE); title.setPadding(dp(7),dp(5),dp(7),0); content.addView(title);
        subtitle=tv(sub,13); subtitle.setTextColor(Color.rgb(226,206,235)); subtitle.setPadding(dp(8),0,dp(8),dp(7)); content.addView(subtitle);
        View line=new View(this); GradientDrawable lg=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{Color.TRANSPARENT,Color.rgb(255,72,149),Color.rgb(169,76,218),Color.TRANSPARENT}); line.setBackground(lg); LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-1,dp(2)); ll.setMargins(dp(14),0,dp(14),dp(7)); content.addView(line,ll);
        addBottomNav(); animate(mini); animate(bn); animate(title); animate(subtitle);
    }

    TextView refCard(String text,int size){
        TextView v=tv(text,size); v.setTextColor(Color.rgb(55,30,57)); v.setGravity(Gravity.CENTER); v.setPadding(dp(16),dp(16),dp(16),dp(16));
        GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{Color.rgb(255,252,254),Color.rgb(255,225,239)}); g.setCornerRadius(dp(22)); g.setStroke(dp(1),Color.rgb(239,122,177)); v.setBackground(g); v.setElevation(dp(4));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(150)); lp.setMargins(dp(7),dp(7),dp(7),dp(7)); v.setLayoutParams(lp); animate(v); return v;
    }
    Button answerBtn(String text, boolean blue){
        Button b=new Button(this); b.setText(text); b.setTextSize(15); b.setTextColor(Color.WHITE); b.setAllCaps(false); b.setGravity(Gravity.CENTER); b.setMinHeight(0); b.setMinimumHeight(0); b.setPadding(dp(14),dp(6),dp(14),dp(6));
        GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,blue?new int[]{Color.rgb(20,170,241),Color.rgb(55,78,221)}:new int[]{Color.rgb(255,55,142),Color.rgb(221,35,118)}); g.setCornerRadius(dp(20)); g.setStroke(dp(1),Color.argb(100,255,255,255)); b.setBackground(g); b.setElevation(dp(5)); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(56)); lp.setMargins(dp(8),dp(5),dp(8),dp(5)); b.setLayoutParams(lp); animateButton(b); return b;
    }
    void addBottomNav(){
        LinearLayout nav=new LinearLayout(this); nav.setGravity(Gravity.CENTER); nav.setPadding(dp(4),dp(5),dp(4),dp(6)); GradientDrawable ng=new GradientDrawable(); ng.setColor(Color.rgb(14,10,31)); ng.setStroke(dp(1),Color.rgb(91,51,105)); nav.setBackground(ng);
        String[] ns={"⌂\nHome","◉\nScore","🎁\nRewards","♙\nProfile"}; Runnable[] rs={()->showHome(),()->showScoreBoard(),()->rewards(),()->showProfile()};
        for(int i=0;i<4;i++){ TextView n=tv(ns[i],11); n.setTextColor(Color.rgb(255,200,225)); n.setGravity(Gravity.CENTER); final Runnable r=rs[i]; n.setOnClickListener(v->{beep();r.run();}); nav.addView(n,new LinearLayout.LayoutParams(0,dp(52),1)); }
        root.addView(nav,new LinearLayout.LayoutParams(-1,dp(62)));
    }


    void animate(View v){ v.setAlpha(0f); v.setTranslationY(dp(12)); v.setScaleX(.97f); v.setScaleY(.97f); v.animate().alpha(1f).translationY(0).scaleX(1).scaleY(1).setDuration(420).setInterpolator(new DecelerateInterpolator()).start(); }
    void animateButton(View v){
        v.setOnTouchListener((x,e)->{ if(e.getAction()==MotionEvent.ACTION_DOWN){beep();x.animate().scaleX(.96f).scaleY(.96f).setDuration(70).start();} else if(e.getAction()==MotionEvent.ACTION_UP||e.getAction()==MotionEvent.ACTION_CANCEL)x.animate().scaleX(1).scaleY(1).setDuration(150).setInterpolator(new OvershootInterpolator()).start(); return false;});
        v.setAlpha(0f); v.setTranslationY(dp(10)); v.setScaleX(.98f); v.setScaleY(.98f);
        v.animate().alpha(1f).translationY(0).scaleX(1f).scaleY(1f).setDuration(380).setInterpolator(new DecelerateInterpolator()).start();
    }
    void hearts(){
        TextView h=tv("💗   ✨   💕   ✨   💗",18); h.setTextColor(pink()); content.addView(h);
        h.setAlpha(0f); h.animate().alpha(1f).translationY(-dp(6)).setDuration(700).setInterpolator(new DecelerateInterpolator()).start();
        h.animate().translationX(dp(5)).setDuration(1200).withEndAction(()->h.animate().translationX(-dp(5)).setDuration(1200).start()).start();
    }
    void confetti(){ String[] a={"🎉","✨","💖","💕","🎊","⭐","💗","🥰"}; for(int i=0;i<18;i++){ TextView p=tv(a[random.nextInt(a.length)],22); content.addView(p); p.setAlpha(0); p.animate().alpha(1).translationX(random.nextInt(dp(181))-dp(90)).translationY(-(dp(80)+random.nextInt(dp(180)))).rotationBy(random.nextInt(360)-180).setDuration(900+random.nextInt(400)).withEndAction(()->p.animate().alpha(0).setDuration(250).start()).start(); } }
    void beep(){ if(!soundOn)return; try{ android.media.ToneGenerator tg=new android.media.ToneGenerator(android.media.AudioManager.STREAM_NOTIFICATION,70); tg.startTone(android.media.ToneGenerator.TONE_PROP_BEEP,120); new Handler().postDelayed(tg::release,180); }catch(Exception ignored){} }
    void startMusic(){ if(!musicOn||music!=null)return; int id=getResources().getIdentifier("romantic_music","raw",getPackageName()); if(id!=0){ music=MediaPlayer.create(this,id); if(music!=null){music.setLooping(true);music.setVolume(.22f,.22f);music.start();}} }
    void stopMusic(){ if(music!=null){try{music.stop();}catch(Exception ignored){} music.release(); music=null;} }

    void showSplash(){
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setGravity(Gravity.CENTER);
        GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{Color.rgb(7,8,24),Color.rgb(83,18,91),Color.rgb(18,22,55)}); root.setBackground(bg);
        ImageView icon=new ImageView(this); icon.setImageResource(com.arjunsara.couplefun.R.mipmap.ic_launcher); icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        root.addView(icon,new LinearLayout.LayoutParams(dp(150),dp(150)));
        TextView t=tv("Mogudu Pellam",34); t.setTypeface(Typeface.DEFAULT,Typeface.BOLD); t.setTextColor(Color.WHITE); root.addView(t);
        TextView sub=tv("Together Forever • Love • Fun",16); sub.setTextColor(Color.rgb(255,210,230)); root.addView(sub);
        TextView names=tv("❤️  Made for Two Hearts  ❤️",18); names.setTextColor(gold()); root.addView(names);
        setContentView(root);
        animate(icon); animate(t); animate(sub); animate(names);
        new Handler().postDelayed(()->{ if(partner1.isEmpty()||partner2.isEmpty()) showWelcome(); else showHome(); },1100);
    }

    void showWelcome(){
        base("Mogudu Pellam ❤️","Love • Fun • Forever");
        hearts();
        TextView hero=tv("Not just a couple…\nA beautiful journey of love, laughter and memories. 🥰",21);
        hero.setTypeface(Typeface.DEFAULT,Typeface.BOLD); content.addView(hero);
        Button start=btn("💖 START OUR JOURNEY  ›"); start.setOnClickListener(v->showSetup()); content.addView(start);
        Button lang=outlineBtn("🌐 Choose Language  •  English"); lang.setOnClickListener(v->Toast.makeText(this,"English is ready ❤️",Toast.LENGTH_SHORT).show()); content.addView(lang);
        content.addView(tv("Happy Birthday Chinna Papa ❣️",14));
    }

    void showSetup(){ base("Let's Get Started! 💕","Enter your names"); EditText a=edit("Partner 1 name"); EditText b=edit("Partner 2 name"); content.addView(a);content.addView(b);
        TextView avatarTitle=tv("💕 Choose Your Couple Avatars",18); avatarTitle.setGravity(Gravity.CENTER); avatarTitle.setTypeface(Typeface.DEFAULT,Typeface.BOLD); content.addView(avatarTitle);
        LinearLayout avatarCard=new LinearLayout(this); avatarCard.setOrientation(LinearLayout.VERTICAL); avatarCard.setGravity(Gravity.CENTER); GradientDrawable acg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{Color.rgb(255,245,250),Color.rgb(255,224,239)}); acg.setCornerRadius(dp(28)); acg.setStroke(dp(2),Color.rgb(238,86,154)); avatarCard.setBackground(acg); avatarCard.setPadding(dp(12),dp(14),dp(12),dp(14));
        TextView avatarPreview=tv(avatar1+"     ❤️     "+avatar2,52); avatarPreview.setGravity(Gravity.CENTER); avatarCard.addView(avatarPreview,new LinearLayout.LayoutParams(-1,dp(100)));
        TextView hint=tv("Tap an avatar below to choose your look",13); hint.setGravity(Gravity.CENTER); hint.setTextColor(Color.rgb(125,80,105)); avatarCard.addView(hint);
        TextView p1label=tv("PARTNER 1  👨🏻",13); p1label.setGravity(Gravity.CENTER); p1label.setTypeface(Typeface.DEFAULT,Typeface.BOLD); p1label.setTextColor(Color.rgb(170,45,105)); avatarCard.addView(p1label);
        LinearLayout boysRow=new LinearLayout(this); boysRow.setGravity(Gravity.CENTER); String[] boys={"👨🏻","👨🏼","👨🏽","👨🏾","👨🏿","🧑🏻","🧑🏼","🧑🏽"};
        for(String av:boys){ Button x=avatarOption(av); x.setOnClickListener(v->{avatar1=av; avatarPreview.setText(avatar1+"     ❤️     "+avatar2);}); boysRow.addView(x); } avatarCard.addView(boysRow);
        TextView p2label=tv("PARTNER 2  👩🏻",13); p2label.setGravity(Gravity.CENTER); p2label.setTypeface(Typeface.DEFAULT,Typeface.BOLD); p2label.setTextColor(Color.rgb(170,45,105)); avatarCard.addView(p2label);
        LinearLayout girlsRow=new LinearLayout(this); girlsRow.setGravity(Gravity.CENTER); String[] girls={"👩🏻","👩🏼","👩🏽","👩🏾","👩🏿","🧑🏻‍🦰","👩🏻‍🦱","👩🏼‍🦱"};
        for(String av:girls){ Button x=avatarOption(av); x.setOnClickListener(v->{avatar2=av; avatarPreview.setText(avatar1+"     ❤️     "+avatar2);}); girlsRow.addView(x); } avatarCard.addView(girlsRow); content.addView(avatarCard,new LinearLayout.LayoutParams(-1,-2));
        Button start=btn("CONTINUE  💖"); start.setOnClickListener(v->{ if(a.getText().toString().trim().isEmpty()||b.getText().toString().trim().isEmpty()){Toast.makeText(this,"Please enter both names ❤️",Toast.LENGTH_SHORT).show();return;} partner1=a.getText().toString().trim();partner2=b.getText().toString().trim();save();showHome(); });content.addView(start);
        Button back=outlineBtn("BACK"); back.setOnClickListener(v->showWelcome()); content.addView(back);
    }
    Button avatarOption(String av){
        Button b=new Button(this); b.setText(av); b.setTextSize(28); b.setGravity(Gravity.CENTER); b.setPadding(0,0,0,0); b.setAllCaps(false); GradientDrawable g=new GradientDrawable(); g.setColor(Color.WHITE); g.setCornerRadius(dp(18)); g.setStroke(dp(1),Color.rgb(245,171,205)); b.setBackground(g); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(dp(50),dp(50)); p.setMargins(dp(3),dp(5),dp(3),dp(5)); b.setLayoutParams(p); return b;
    }
    EditText edit(String hint){
        EditText e=new EditText(this); e.setHint(hint); e.setHintTextColor(darkMode?Color.rgb(185,171,198):Color.rgb(130,105,120));
        e.setTextColor(textMain()); e.setTextSize(16); e.setSingleLine(false); e.setPadding(dp(18),dp(10),dp(18),dp(10));
        GradientDrawable g=new GradientDrawable(); g.setColor(darkMode?Color.argb(90,255,255,255):Color.WHITE); g.setCornerRadius(dp(18));
        g.setStroke(dp(1),darkMode?Color.argb(100,255,255,255):Color.rgb(239,177,205)); e.setBackground(g);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,dp(56)); p.setMargins(dp(8),dp(6),dp(8),dp(6)); e.setLayoutParams(p); return e;
    }
    void addBack(){ Button b=outlineBtn("←  Back to Home"); b.setOnClickListener(v->showHome()); content.addView(b); }

    Button feature(String label){
        String[] parts=label.split("\\n",2);
        return premiumTile(parts.length>0?parts[0]:"💗",parts.length>1?parts[1]:label);
    }

    void addFeatureGrid(String[] labels, Runnable[] actions){
        for(int i=0;i<labels.length;i+=3){
            LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            for(int j=i;j<Math.min(i+3,labels.length);j++){
                Button b=feature(labels[j]); final Runnable r=actions[j]; b.setOnClickListener(v->{beep();r.run();});
                b.setAlpha(0f); b.setTranslationY(dp(18)); b.setScaleX(.94f); b.setScaleY(.94f);
                b.animate().alpha(1f).translationY(0).scaleX(1f).scaleY(1f).setStartDelay((j-i)*90L).setDuration(420).setInterpolator(new OvershootInterpolator(0.9f)).start();
                row.addView(b);
            }
            content.addView(row,new LinearLayout.LayoutParams(-1,-2));
        }
    }

    void showHome(){
        base("Mogudu Pellam ❤️",partner1+"  &  "+partner2); hearts();

        // Reference-style couple hero: logo + selected avatars + names
        LinearLayout hero=new LinearLayout(this); hero.setOrientation(LinearLayout.VERTICAL); hero.setGravity(Gravity.CENTER);
        hero.setPadding(dp(10),dp(8),dp(10),dp(8));
        GradientDrawable hg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{Color.rgb(255,251,253),Color.rgb(255,220,238)});
        hg.setCornerRadius(dp(24)); hg.setStroke(dp(2),Color.rgb(243,104,168)); hero.setBackground(hg); hero.setElevation(dp(6));

        LinearLayout top=new LinearLayout(this); top.setGravity(Gravity.CENTER);
        ImageView logo=new ImageView(this); logo.setImageResource(R.mipmap.ic_launcher); logo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GradientDrawable lg=new GradientDrawable(); lg.setShape(GradientDrawable.OVAL); lg.setStroke(dp(2),Color.rgb(255,76,156)); logo.setBackground(lg);
        top.addView(logo,new LinearLayout.LayoutParams(dp(62),dp(62)));
        TextView pair=tv(avatar1+"     ❤️     "+avatar2,40); pair.setTextColor(Color.rgb(58,28,58));
        top.addView(pair,new LinearLayout.LayoutParams(0,dp(66),1)); hero.addView(top);
        TextView names=tv(partner1+"  &  "+partner2,19); names.setTypeface(Typeface.DEFAULT,Typeface.BOLD); names.setTextColor(Color.rgb(53,27,55)); hero.addView(names);
        TextView forever=tv("Together Forever  •  Love  •  Fun  •  Memories",12); forever.setTextColor(Color.rgb(151,39,96)); hero.addView(forever);
        TextView avatarHint=tv("Tap Profile anytime to change your couple avatars 💕",11); avatarHint.setTextColor(Color.rgb(166,55,111)); hero.addView(avatarHint);
        LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,dp(158)); hp.setMargins(dp(6),dp(2),dp(6),dp(5)); content.addView(hero,hp);

        // Compact premium stats strip
        TextView stats=tv("👑 LEVEL "+level()+"     •     ✨ XP "+xp+"     •     🪙 "+coins+"     •     💗 "+loveMeter+"%",14);
        stats.setTextColor(Color.rgb(65,31,65));
        GradientDrawable sg=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.rgb(255,246,251),Color.rgb(247,220,255),Color.rgb(255,237,247)});
        sg.setCornerRadius(dp(22)); sg.setStroke(dp(1),Color.rgb(230,115,177)); stats.setBackground(sg);
        LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,dp(52)); sp.setMargins(dp(8),dp(3),dp(8),dp(4)); content.addView(stats,sp);
        content.addView(sectionTitle("💗  COUPLE DASHBOARD"));

        String[] labels={"💬\n100+ Couple\nQuestions","🎂\n35 Birthday\nWishes","💍\n35 Anniversary\nWishes",
                "💌\nDaily Love\nMessage","🎡\nSpin the\nWheel","🎭\nTruth or\nDare",
                "🔥\nCouple\nChallenge","😂\nFunny\nPunishment","💗\nLove\nMeter",
                "🏆\nXP + Levels\nAchievements","🪙\nCoins &\nRewards","📸\nMemories +\nCaptions",
                "❤️\nOur Love\nStory","🎁\nSurprise\nBox","📊\nOur Score\n& History","⚙️\nSettings\n& More"};
        Runnable[] acts={()->showCoupleQuestions(),()->showBirthday(),()->showAnniversary(),()->dailyMessage(),()->spinWheel(),()->truthDare(),
                ()->challenge(),()->punishment(),()->loveMeter(),()->achievements(),()->rewards(),()->memories(),()->showLoveStory(),()->surpriseBox(),()->showScoreBoard(),()->showProfile()};
        for(int i=0;i<labels.length;i+=3){
            LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL); row.setGravity(Gravity.CENTER);
            for(int j=i;j<Math.min(i+3,labels.length);j++){
                String[] parts=labels[j].split("\\n",2);
                Button b=premiumTile(parts[0],parts.length>1?parts[1]:labels[j]);
                final Runnable r=acts[j]; b.setOnClickListener(v->{beep();r.run();});
                row.addView(b);
            }
            content.addView(row,new LinearLayout.LayoutParams(-1,-2));
        }
        TextView foot=tv("♥  Mogudu Pellam  ♥\nLove  •  Fun  •  Forever",12); foot.setTextColor(Color.rgb(224,196,229));
        content.addView(foot);
    }

    void showCoupleQuestions(){
        base("100+ Couple Questions 💬","Get closer • Laugh • Know each other better"); hearts();
        TextView card=refCard("💗\n100+ Fun Questions\n\nAsk, answer and discover how well you know each other.\n\n20 questions per game • 3 rounds",20); content.addView(card);
        Button start=btn("💖 START QUESTIONS  ›"); start.setOnClickListener(v->showQuiz()); content.addView(start); addBack();
    }


    void showGameSelection(){
        base("Choose a Game 🎮","Play • Laugh • Get Closer ❤️"); hearts();
        String[] titles={"🛡️  Truth or Dare","🏆  Couple Challenge","📊  Our Score"}; String[] subs={"Truths, dares & endless fun","Cute challenges to do together","Check your score, stats & history"}; Runnable[] rs={()->truthDare(),()->challenge(),()->showScoreBoard()};
        for(int i=0;i<3;i++){ LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.VERTICAL); TextView c=refCard(titles[i]+"\n"+subs[i],18); row.addView(c); final Runnable r=rs[i]; c.setOnClickListener(v->r.run()); content.addView(row,new LinearLayout.LayoutParams(-1,dp(150))); }
        Button play=btn("💖 PLAY NOW"); play.setOnClickListener(v->showQuiz()); content.addView(play); addBack();
    }


    void showScoreBoard(){
        base("Score Board 🏆",partner1+"  VS  "+partner2); hearts();
        int r1=data.getInt("r1",0),r2=data.getInt("r2",0),r3=data.getInt("r3",0),best=data.getInt("best",0);
        TextView couple=refCard(avatar1+"   "+partner1+"\n        💗 VS 💗\n"+avatar2+"   "+partner2,18); content.addView(couple);
        content.addView(refCard("ROUND 1  •  Who Knows Better\n"+r1+" points ❤️",16));
        content.addView(refCard("ROUND 2  •  Truth or Dare\n"+r2+" points 🔥",16));
        content.addView(refCard("ROUND 3  •  Couple Challenge\n"+r3+" points 🏆",16));
        content.addView(refCard("TOTAL SCORE\n"+best+"  ❤️",25));
        Button play=btn("💖 PLAY AGAIN"); play.setOnClickListener(v->showQuiz()); content.addView(play); addBack();
    }


    void showQuiz(){ roundNumber=1; roundScore=0; totalGameScore=0; quizIndex=0; quizScore=0; p1Choice=-1; showQuestion(); }

    void showQuestion(){
        if(quizIndex>=20){ data.edit().putInt("r"+roundNumber,roundScore).apply(); totalGameScore+=roundScore; if(roundNumber<3){showRoundResult();return;} int best=Math.max(data.getInt("best",0),totalGameScore);data.edit().putInt("best",best).apply();showFinalResult();return; }
        base("Round "+roundNumber+" 💗","Question "+(quizIndex+1)+" / 20"); hearts();
        TextView q=refCard("💬\n"+questions[(quizIndex+(roundNumber-1)*20)%questions.length],21); content.addView(q);
        TextView hint=tv(p1Choice<0?"First: "+partner1+" choose ❤️":"Now: "+partner2+" choose ❤️",14); hint.setTextColor(gold()); content.addView(hint);
        Button p1=answerBtn(avatar1+"  "+partner1,true); Button p2=answerBtn(avatar2+"  "+partner2,false); content.addView(p1);content.addView(p2);
        p1.setOnClickListener(v->answerQuestion(0));p2.setOnClickListener(v->answerQuestion(1));
        Button skip=outlineBtn("SKIP QUESTION");skip.setOnClickListener(v->{p1Choice=-1;quizIndex++;showQuestion();});content.addView(skip); addBack();
    }


    void answerQuestion(int choice){
        if(p1Choice<0){ p1Choice=choice; beep(); showQuestion(); return; }
        if(choice==p1Choice){roundScore+=10;quizScore+=10;gain(4,2);Toast.makeText(this,"Same answer! +10 ❤️",Toast.LENGTH_SHORT).show();}
        else {roundScore+=5;quizScore+=5;gain(2,1);Toast.makeText(this,"Close answer! +5 💕",Toast.LENGTH_SHORT).show();}
        p1Choice=-1; quizIndex++; beep(); showQuestion();
    }

    void showRoundResult(){
        base("Round "+roundNumber+" Complete! 🎉",partner1+" & "+partner2+" • Great job!"); confetti();
        TextView trophy=refCard("🏆\nROUND "+roundNumber+"\n\nYour Score\n"+roundScore+" / 200 ❤️\n\nGreat Job! Keep Going 🥰",25); content.addView(trophy);
        Button next=btn(roundNumber==2?"NEXT ROUND 3 🔥":"NEXT ROUND 2 💕");next.setOnClickListener(v->{roundNumber++;quizIndex=0;roundScore=0;p1Choice=-1;showQuestion();});content.addView(next);Button details=outlineBtn("VIEW SCORE BOARD");details.setOnClickListener(v->showScoreBoard());content.addView(details);
    }


    void showFinalResult(){
        base("Final Result 💖",partner1+" & "+partner2); confetti(); int score=data.getInt("best",totalGameScore); int percent=Math.min(100,40+(score/10)); String label=score>=180?"Perfect Couple 👑":score>=150?"Super Jodi! 😍":score>=120?"Cute Couple 🥰":"Still Learning Each Other 💕";
        TextView result=refCard("💗\nCompatibility\n"+percent+"%\n\n"+label+"\n\nYou both know each other really well.\nKeep loving & keep laughing! ❤️",22); content.addView(result);
        Button again=btn("💖 PLAY AGAIN");again.setOnClickListener(v->showQuiz());content.addView(again);Button share=outlineBtn("📤 SHARE RESULT");share.setOnClickListener(v->shareText(partner1+" & "+partner2+" ❤️\nCompatibility: "+percent+"%\n"+label+"\nMogudu Pellam"));content.addView(share);Button home=outlineBtn("HOME");home.setOnClickListener(v->showHome());content.addView(home);
    }


    void dailyMessage(){ base("Daily Love Message 💌","A little love for today"); hearts(); Calendar c=Calendar.getInstance(); int idx=(c.get(Calendar.DAY_OF_YEAR)-1)%messages.length; TextView out=refCard("💌\nDAILY LOVE MESSAGE\n\n"+messages[idx]+"\n\n✨ Come back tomorrow for a new message",22);content.addView(out);gain(5,2);addBack(); }

    void spinWheel(){ base("Spin the Wheel 🎡","Let love decide! • Tap SPIN"); hearts(); TextView result=refCard("🎡\n\nSPIN THE WHEEL\n\n❤️\nTap below for a surprise",24);content.addView(result); String[] opts={"Give a hug 🤗","Say 3 compliments 💕","Plan a date 🍿","Take a selfie 📸","Give a forehead kiss 😘","Tell a secret ❤️","Dance together 💃","Make a sweet promise 💍"}; Button spin=btn("🎡  SPIN");spin.setOnClickListener(v->{result.setText("🎡\n\n"+opts[random.nextInt(opts.length)]);result.animate().rotationBy(720).setDuration(900).setInterpolator(new DecelerateInterpolator()).start();gain(10,5);confetti();});content.addView(spin);addBack(); }


    void truthDare(){ base("Truth or Dare 🎭",partner1+" & "+partner2); hearts(); TextView out=refCard("Choose one! 💕",22);content.addView(out);Button t=btn("💬 TRUTH");Button d=btn("🔥 DARE");t.setOnClickListener(v->{out.setText("💬 TRUTH\n\n"+truths[random.nextInt(truths.length)]);gain(8,3);});d.setOnClickListener(v->{out.setText("🔥 DARE\n\n"+dares[random.nextInt(dares.length)]);gain(10,4);});content.addView(t);content.addView(d);addBack(); }

    void challenge(){ base("Challenge Time! 🔥","Complete it together"); hearts(); TextView out=refCard("🔥\nCHALLENGE\n\n"+challenges[random.nextInt(challenges.length)],22);content.addView(out);Button next=btn("🔥 NEW CHALLENGE");next.setOnClickListener(v->{out.setText("🔥\nCHALLENGE\n\n"+challenges[random.nextInt(challenges.length)]);gain(12,5);});content.addView(next);Button win=btn("🏆 WE COMPLETED IT!");win.setOnClickListener(v->{gain(25,10);confetti();Toast.makeText(this,"Challenge completed! +25 XP +10 Coins",Toast.LENGTH_SHORT).show();});content.addView(win);addBack(); }

    void punishment(){
        base("Punishment Time 😂","Oops! Wrong answer 😜 • Loser gets a cute punishment"); hearts(); TextView out=refCard("😜\nPunishment Time!\n\n"+punishments[random.nextInt(punishments.length)],21);content.addView(out);
        Button next=btn("😂 RANDOM PUNISHMENT");next.setOnClickListener(v->out.setText("😜\nPunishment Time!\n\n"+punishments[random.nextInt(punishments.length)]));content.addView(next);Button done=btn("❤️ ACCEPT CHALLENGE");done.setOnClickListener(v->{gain(8,3);confetti();Toast.makeText(this,"Punishment completed! +8 XP",Toast.LENGTH_SHORT).show();});content.addView(done);Button again=outlineBtn("TRY AGAIN");again.setOnClickListener(v->showQuiz());content.addView(again);
    }


    void loveMeter(){ base("Love Meter 💗","How strong is your love today?"); SeekBar bar=new SeekBar(this);bar.setMax(100);bar.setProgress(loveMeter);content.addView(bar);TextView value=tv(loveMeter+"% ❤️",28);content.addView(value);bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){public void onProgressChanged(SeekBar s,int p,boolean f){loveMeter=p;value.setText(p+"% ❤️");}public void onStartTrackingTouch(SeekBar s){}public void onStopTrackingTouch(SeekBar s){save();gain(5,2);}});Button magic=btn("💖 Fill With Love");magic.setOnClickListener(v->{loveMeter=Math.min(100,loveMeter+10);bar.setProgress(loveMeter);save();gain(10,5);});content.addView(magic);addBack(); }

    int level(){return Math.max(1,(xp/100)+1);} String levelName(){String[] n={"Newly in Love","Sweet Couple","Love Birds","Romance Pro","Forever Team","Soulmate Legends"};return n[Math.min(n.length-1,level()-1)];}
    void achievements(){ base("Achievements 🏆","Level "+level()+" • "+levelName()); String[] a={"💖 First Love — Open the app","🎯 Quiz Starter — Answer questions","🔥 Challenge Accepted — Complete a challenge","💌 Daily Heart — Read a daily message","🎡 Wheel Spinner — Spin the wheel","💗 Love Meter — Set your love score","📸 Memory Maker — Save a memory","❤️ Story Keeper — Create your story","🪙 Coin Collector — Earn 100 coins","👑 Romance Legend — Reach Level 5"}; for(String s:a)content.addView(tv(s,18));addBack(); }
    void rewards(){ base("Coins & Rewards 🪙","Coins: "+coins+" • Level "+level()); for(String r:rewards){Button b=btn(r+" • 30 🪙");b.setOnClickListener(v->{if(coins>=30){coins-=30;save();Toast.makeText(this,"Reward unlocked: "+r,Toast.LENGTH_SHORT).show();}else Toast.makeText(this,"Need 30 coins 🪙",Toast.LENGTH_SHORT).show();});content.addView(b);}addBack(); }

    void memories(){ base("Memories + Captions 📸","Save little moments forever"); EditText m=edit("Write a memory..."); EditText cap=edit("Caption (optional)");content.addView(m);content.addView(cap);Button save=btn("💾 Save Memory");save.setOnClickListener(v->{String text=m.getText().toString().trim();if(text.isEmpty())return;String old=data.getString("memories","");String line="❤️ "+text+"\n✨ "+cap.getText().toString().trim()+"\n\n";data.edit().putString("memories",line+old).apply();gain(15,5);m.setText("");cap.setText("");Toast.makeText(this,"Memory saved ❤️",Toast.LENGTH_SHORT).show();showMemoriesList();});content.addView(save);showMemoriesList(); }
    void showMemoriesList(){String old=data.getString("memories","");if(!old.isEmpty()){content.addView(tv("Saved Memories",20));content.addView(tv(old,17));}addBack();}

    void showLoveStory(){ String met=data.getString("met","");String date=data.getString("date","");String memory=data.getString("storyMemory","");String love=data.getString("storyLove","");String story=data.getString("story","");base("Our Love Story ❤️",partner1+" ❤️ "+partner2);EditText e1=edit("❤️ How we met");e1.setText(met);EditText e2=edit("📅 First meeting / date");e2.setText(date);EditText e3=edit("💕 Special memory");e3.setText(memory);EditText e4=edit("🥰 What we love about each other");e4.setText(love);EditText e5=edit("✍️ Complete love story");e5.setMinLines(5);e5.setGravity(Gravity.TOP);e5.setText(story);content.addView(e1);content.addView(e2);content.addView(e3);content.addView(e4);content.addView(e5);Button create=btn("✨ Create My Love Story");create.setOnClickListener(v->{data.edit().putString("met",e1.getText().toString()).putString("date",e2.getText().toString()).putString("storyMemory",e3.getText().toString()).putString("storyLove",e4.getText().toString()).putString("story",e5.getText().toString()).apply();gain(20,8);showLoveStoryCard(e1.getText().toString(),e2.getText().toString(),e3.getText().toString(),e4.getText().toString(),e5.getText().toString());});content.addView(create);Button view=btn("💖 View My Story Card");view.setOnClickListener(v->showLoveStoryCard(met,date,memory,love,story));content.addView(view);addBack(); }
    String compliment(String all){all=all.toLowerCase();if(all.contains("childhood")||all.contains("school")||all.contains("college"))return "You grew through beautiful memories into an even more beautiful love story. ❤️";if(all.contains("distance"))return "Distance tested you, but your love kept choosing each other. 💕";if(all.contains("fight")||all.contains("sorry"))return "Real love chooses understanding and forgiveness again and again. ❤️";return "You didn’t just meet by chance… you created a beautiful journey together. The little memories, care and understanding make your relationship truly special. ❤️";}
    void showLoveStoryCard(String met,String date,String memory,String love,String story){base("Your Love Story 💖",partner1+" ❤️ "+partner2);hearts();String comp=compliment(met+" "+date+" "+memory+" "+love+" "+story);String s="❤️ "+partner1+" & "+partner2+" ❤️\n\n"+section("How we met",met)+section("First meeting / date",date)+section("Special memory",memory)+section("What we love about each other",love)+section("Our complete love story",story)+"✨ Love Story Compliment ✨\n"+comp;content.addView(tv(s,18));Button sh=btn("📤 Share My Love Story ❤️");sh.setOnClickListener(v->shareLoveStoryCard(comp,met,date,memory,love,story));content.addView(sh);Button edit=btn("✏️ Edit My Love Story");edit.setOnClickListener(v->showLoveStory());content.addView(edit);addBack();}
    String section(String h,String v){return v==null||v.trim().isEmpty()?"":h+"\n"+v+"\n\n";}
    void shareLoveStoryCard(String comp,String met,String date,String memory,String love,String story){try{Bitmap b=Bitmap.createBitmap(1080,1500,Bitmap.Config.ARGB_8888);Canvas c=new Canvas(b);c.drawColor(Color.rgb(35,18,45));Paint p=new Paint(1);p.setColor(Color.WHITE);p.setTextAlign(Paint.Align.CENTER);p.setTextSize(48);p.setTypeface(Typeface.DEFAULT_BOLD);c.drawText("❤️ "+partner1+" & "+partner2+" ❤️",540,85,p);p.setTextAlign(Paint.Align.LEFT);p.setTextSize(30);float y=145;String[] sec={section("How we met",met),section("First meeting / date",date),section("Special memory",memory),section("What we love",love),section("Our love story",story),"Love Story Compliment\n"+comp};for(String x:sec){for(String line:wrap(x,43)){if(y>1410)break;c.drawText(line,50,y,p);y+=42;}y+=15;}p.setTextAlign(Paint.Align.CENTER);p.setTextSize(27);c.drawText("Made with ❤️ in Mogudu Pellam",540,1465,p);File f=new File(getCacheDir(),"love_story_"+System.currentTimeMillis()+".png");FileOutputStream o=new FileOutputStream(f);b.compress(Bitmap.CompressFormat.PNG,100,o);o.close();Uri u=FileProvider.getUriForFile(this,getPackageName()+".fileprovider",f);Intent i=new Intent(Intent.ACTION_SEND);i.setType("image/png");i.putExtra(Intent.EXTRA_STREAM,u);i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);startActivity(Intent.createChooser(i,"Share Our Love Story ❤️"));}catch(Exception e){shareText("Our Love Story ❤️\n"+partner1+" & "+partner2+"\n"+story+"\n\n"+comp);}}
    ArrayList<String> wrap(String s,int max){ArrayList<String> out=new ArrayList<>();for(String para:s.split("\\n")){String cur="";for(String w:para.trim().split("\\s+")){if(w.isEmpty())continue;if((cur+" "+w).trim().length()>max){if(!cur.isEmpty())out.add(cur);cur=w;}else cur=(cur+" "+w).trim();}if(!cur.isEmpty())out.add(cur);}return out;}

    void showBirthday(){
        base("35 Birthday Wishes 🎂","Choose from 35 unique wishes for your loved one");
        TextView out=premiumCard(birthday[random.nextInt(birthday.length)],20); content.addView(out);
        Button next=btn("🎂 BROWSE NEXT WISH"); next.setOnClickListener(v->{out.setText(birthday[random.nextInt(birthday.length)]);gain(2,1);}); content.addView(next);
        Button share=outlineBtn("📤 SHARE BIRTHDAY WISH"); share.setOnClickListener(v->shareText("Happy Birthday Chinna Papa ❣️\n\n"+out.getText())); content.addView(share); addBack();
    }
    void showAnniversary(){
        base("35 Anniversary Wishes 💍","Choose from 35 unique wishes for your special one");
        TextView out=premiumCard(anniversary[random.nextInt(anniversary.length)],20); content.addView(out);
        Button next=btn("💍 BROWSE NEXT WISH"); next.setOnClickListener(v->{out.setText(anniversary[random.nextInt(anniversary.length)]);gain(2,1);}); content.addView(next);
        Button share=outlineBtn("📤 SHARE ANNIVERSARY WISH"); share.setOnClickListener(v->shareText(out.getText().toString())); content.addView(share); addBack();
    }
    void surpriseBox(){ base("Surprise Box 🎁","Open one surprise at a time"); hearts(); TextView out=refCard("🎁\n\nTAP TO OPEN\n\nA cute surprise is waiting for you 💕",23);content.addView(out);String[] ss={"Send a voice note saying I love you ❤️","Plan a sunset walk 🌅","Give a surprise snack 🍫","Write a tiny love letter 💌","Make a couple selfie collage 📸","Cook something together 🍳","Watch your first movie again 🎬","Tell your partner one thing you appreciate 🥰","Plan your dream trip ✈️","Give a warm hug for 30 seconds 🤗"};Button open=btn("🎁 OPEN SURPRISE");open.setOnClickListener(v->{out.setText("✨ SURPRISE ✨\n\n"+ss[random.nextInt(ss.length)]);gain(12,5);confetti();});content.addView(open);addBack(); }


    void shareResult(){shareText("Our Couple Result ❤️\n"+partner1+" & "+partner2+"\nLove Meter: "+loveMeter+"%\nLevel: "+level()+" - "+levelName()+"\nXP: "+xp+"\nCoins: "+coins+" 🪙\nMogudu Pellam ❤️");}
    void shareText(String text){try{Intent i=new Intent(Intent.ACTION_SEND);i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,text);startActivity(Intent.createChooser(i,"Share Result ❤️"));}catch(Exception e){Toast.makeText(this,"Sharing unavailable",Toast.LENGTH_SHORT).show();}}

    void showProfile(){
        base("Profile / More 👤",partner1+" & "+partner2+"\nPerfect Together ❤️");
        TextView c=refCard(avatar1+"      ❤️      "+avatar2+"\n\n"+partner1+"  &  "+partner2,22); content.addView(c);
        String[] labels={"📊  Our Score & History","🏆  Achievements","🎁  Redeem Rewards","⚙️  Settings","⭐  How to Play","⭐  Rate Us","📤  Share with Friends"}; Runnable[] acts={()->showScoreBoard(),()->achievements(),()->rewards(),()->showSettings(),()->howToPlay(),()->Toast.makeText(this,"Thank you for supporting Mogudu Pellam! ❤️",Toast.LENGTH_SHORT).show(),()->shareText("We are playing Mogudu Pellam ❤️\n"+partner1+" & "+partner2)};
        for(int i=0;i<labels.length;i++){ Button b=outlineBtn(labels[i]+"  ›");final Runnable r=acts[i];b.setOnClickListener(v->r.run());content.addView(b); }
        Button exit=btn("EXIT GAME");exit.setOnClickListener(v->showHome());content.addView(exit);
    }


    void showSettings(){base("Settings ⚙️","Customize your couple game");Switch music=new Switch(this);music.setText("🎵 Background Music");music.setTextColor(darkMode?Color.WHITE:Color.DKGRAY);music.setChecked(musicOn);content.addView(music);music.setOnCheckedChangeListener((b,c)->{musicOn=c;save();if(c)startMusic();else stopMusic();});Switch sound=new Switch(this);sound.setText("🔊 Sound Effects");sound.setTextColor(darkMode?Color.WHITE:Color.DKGRAY);sound.setChecked(soundOn);content.addView(sound);sound.setOnCheckedChangeListener((b,c)->{soundOn=c;save();});Switch dark=new Switch(this);dark.setText("🌙 Dark Mode");dark.setTextColor(darkMode?Color.WHITE:Color.DKGRAY);dark.setChecked(darkMode);content.addView(dark);dark.setOnCheckedChangeListener((b,c)->{darkMode=c;save();showSettings();});Button names=btn("👫 Change Couple Names");names.setOnClickListener(v->changeNames());content.addView(names);Button reset=btn("🗑 Reset Progress");reset.setOnClickListener(v->{new AlertDialog.Builder(this).setTitle("Reset progress?").setMessage("XP, coins, love meter and memories will be cleared.").setNegativeButton("Cancel",null).setPositiveButton("Reset",(d,w)->{data.edit().clear().putString("p1",partner1).putString("p2",partner2).apply();load();showHome();}).show();});content.addView(reset);Button about=btn("ℹ️ About");about.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("Mogudu Pellam ❤️").setMessage("A fun romantic couple game with questions, wishes, challenges, rewards, memories and Our Love Story.\n\nAndroid app UI is designed for phone/tablet sizes. A responsive web-friendly edition can use the same content.").setPositiveButton("OK",null).show());content.addView(about);addBack();}
    void changeNames(){base("Couple Names ❤️","Update your names anytime");EditText a=edit("Partner 1");a.setText(partner1);EditText b=edit("Partner 2");b.setText(partner2);content.addView(a);content.addView(b);Button save=btn("💾 Save Names");save.setOnClickListener(v->{if(!a.getText().toString().trim().isEmpty()&&!b.getText().toString().trim().isEmpty()){partner1=a.getText().toString().trim();partner2=b.getText().toString().trim();save();showHome();}});content.addView(save);addBack();}
    void howToPlay(){new AlertDialog.Builder(this).setTitle("How to Play ❤️").setMessage("Enter two names, then explore the games.\n\nAnswer love questions, try Truth or Dare, complete challenges, spin the wheel, save memories, build your Love Story, earn XP and coins, unlock rewards and share your results.\n\nTip: Daily Love Message changes each day. Have fun together! 🥰").setPositiveButton("Let's Go",null).show();}

    @Override protected void onPause(){super.onPause();if(isFinishing())stopMusic();}
    @Override protected void onResume(){super.onResume();if(musicOn&&music==null)startMusic();}
    @Override protected void onDestroy(){if(timer!=null)timer.cancel();stopMusic();super.onDestroy();}

    static String[] makeQuestions(){String[] q={
        "Who fell in love first?","Who said I love you first?","Who is more romantic?","Who misses the other more?","Who is more caring?","Who gets jealous faster?","Who gets angry first?","Who apologizes first?","Who is more stubborn?","Who talks more?","Who gives better surprises?","Who knows the partner better?","Who gives the best hugs?","Who has the cutest smile?","Who remembers dates better?","Who takes longer to get ready?","Who spends more time on the phone?","Who makes the partner laugh more?","Who is more protective?","Who forgives faster?","Who plans romantic dates?","Who would travel far for love?","Who says I miss you first?","Who calls just to hear the voice?","Who keeps old photos?","Who is more emotional in movies?","Who is more adventurous?","Who is more patient?","Who chooses love over money?","Who loves more? ❤️",
        "Who sends good morning texts first?","Who sends good night texts first?","Who is more likely to fall asleep on a call?","Who remembers the first chat?","Who remembers the first gift?","Who chooses the restaurant?","Who orders more food?","Who steals food from the other?","Who is better at keeping secrets?","Who laughs at the worst times?","Who takes more selfies?","Who has more romantic ideas?","Who gets shy during compliments?","Who is more likely to plan a surprise trip?","Who is more likely to say sorry even when right?","Who gives better advice?","Who is better at cheering the other up?","Who notices mood changes first?","Who is more likely to write a love letter?","Who is more likely to keep tickets and little memories?",
        "Who would choose a beach date?","Who would choose a mountain date?","Who would choose a movie night?","Who would choose a long drive?","Who would choose a coffee date?","Who would choose a candlelight dinner?","Who would choose a picnic?","Who would choose a gaming night?","Who would choose a photo walk?","Who would choose a surprise date?","Who is more likely to cry during a romantic movie?","Who is more likely to sing badly but proudly?","Who is more likely to dance in public?","Who is more likely to forget where they kept something?","Who is more likely to make a silly joke?","Who is more likely to check on the other when sick?","Who is more likely to make a handmade gift?","Who is more likely to save money for a trip?","Who is more likely to take the first step after an argument?","Who is more likely to say lets talk instead of fighting?",
        "Who would remember a tiny detail from months ago?","Who would recognize the partner by voice?","Who would choose the movie faster?","Who would choose the playlist?","Who is better at taking couple photos?","Who is more likely to give a forehead kiss?","Who is more likely to hug first?","Who is more likely to plan an anniversary?","Who is more likely to celebrate small milestones?","Who is more likely to say you look beautiful today?","Who is more likely to make the other blush?","Who is more likely to be dramatic?","Who is more likely to send a funny meme?","Who is more likely to use cute nicknames?","Who is more likely to start a pillow fight?","Who is more likely to make breakfast?","Who is more likely to share the last bite?","Who is more likely to say lets take one more photo?","Who is more likely to choose matching outfits?","Who is more likely to protect the relationship during a difficult time?"
    };return q;}
    static String[] makeTruths(){return new String[]{"What was the first thing you liked about your partner? ❤️","What was your first impression?","What is your favorite memory together?","What cute habit do you secretly love?","What is your partner's most attractive quality?","When did you realize you were in love?","Where do you want to travel together?","What should never change about your partner?","What is the sweetest thing they did for you?","What dream should you achieve together?","What song reminds you of them?","What is your favorite nickname?","What tiny thing makes you smile?","What is your ideal date?","What is one promise you want to keep?","What is your funniest memory?","What gift from them means the most?","What do you admire most about them?","What is one thing you want to learn together?","What makes you feel safest with them?","What is your favorite photo together?","What is your dream anniversary?","What do you want to say more often?","What is one habit you want to build together?","What makes your relationship unique?","What is one place you want to revisit?","What was your cutest chat moment?","What makes you proud of your partner?","What is one surprise you would love?","What future memory do you want to create?","What is your sweetest inside joke?","What do you miss most when apart?","What makes you laugh every time?","What is your favorite thing to do together?","What is one thing you forgive easily?","What is your favorite way to receive affection?","What is one challenge you overcame together?","What is your favorite time of day together?","What would you write in a love letter?","What is your forever promise?","What makes you choose each other every day?","What is one quality you want to copy from them?","What is your favorite food date?","What is your perfect lazy day together?","What is one dream home idea?","What is your favorite celebration together?","What would you do on a surprise free day?","What is your favorite couple tradition?","What is one thing you want to thank them for?","What is one word for your relationship?"};}
    static String[] makeDares(){return new String[]{"Give your partner 3 genuine compliments. ❤️","Do a funny dance for 20 seconds. 😂","Imitate your partner for 15 seconds. 🤣","Say I love you in 5 funny styles. 😘","Tell a cute romantic line. 💕","Hum a favorite song for 10 seconds. 🎵","Smile at your partner for 15 seconds. 😊","Take a funny couple selfie. 📸","Give 3 cute nicknames. 😍","Describe your partner like a movie ad. 😂","Give a 20-second hug. 🤗","Make a heart with your hands. ❤️","Say one thing you appreciate loudly. 💖","Do a tiny victory dance together. 🎉","Send a sweet text while sitting together. 💌","Tell a joke until they laugh. 😂","Recreate your first selfie pose. 📸","Say a compliment without using beautiful or cute. ✨","Hold hands for one minute. 🤝","Whisper a romantic sentence. 💕","Make up a couple handshake. 🤜🤛","Sing one line from a love song. 🎤","Give a forehead kiss if comfortable. 😘","Plan a mini date in 30 seconds. ⏱️","Say three reasons you are grateful. 🥰","Act like you are meeting for the first time. 😄","Do your best romantic movie pose. 🎬","Tell them one future dream. 🌟","Make your partner laugh using only expressions. 🤣","Say your favorite memory dramatically. 🎭","Draw a tiny heart on paper. ❤️","Give a sincere thank-you speech. 🥹","Do synchronized claps for 15 seconds. 👏","Take a matching-pose photo. 📸","Say a sweet promise for tomorrow. 💍","Describe them using five emojis. 😍","Make a silly nickname song. 🎵","Give a high five and hug. 🙌","Tell them what you noticed first. 💕","Pretend to host your own couple show. 🎤","Make a 10-second love advertisement. 📺","Do a slow-motion high five. 😂","Tell a funny story in 20 seconds. 🤣","Make a tiny paper heart. 💗","Choose a dream destination together. ✈️","Give two compliments about personality. ❤️","Do a cute victory pose. 🏆","Say I choose you today. 💖","End with your best couple smile. 😊"};}
    static String[] makeChallenges(){return new String[]{"Staring Challenge 👀 — eye contact for 30 seconds","No Laugh Challenge 😂 — stay serious for 30 seconds","Song Challenge 🎵 — guess a favorite song","Memory Challenge 🧠 — describe your first date","Imitation Challenge 🤣 — copy each other","Compliment Challenge ❤️ — give compliments for 20 seconds","Emoji Challenge 😍 — explain a message using emojis","Pose Challenge 📸 — best matching pose","Whisper Challenge 🤫 — guess a sentence","Fast Answers ⚡ — answer 10 couple questions","One Word Story ✍️ — build a story one word at a time","Rock Paper Scissors ❤️ — best of five","Mirror Challenge 🪞 — copy movements","No Phone Challenge 📵 — talk for five minutes","Nickname Challenge 😘 — invent new nicknames","Future Challenge 🌟 — plan a dream day","Movie Dialogue Challenge 🎬 — act a scene","Dance Sync 💃 — copy each other's moves","Memory Photo Challenge 📸 — recreate an old photo","Kindness Challenge 🥰 — do three helpful things","Laugh Contest 😂 — first laugh loses","Eye Blink Challenge 👀 — first blink loses","Story Challenge 📖 — tell how you met in 30 seconds","Food Guess Challenge 🍫 — guess a snack blindfolded only if safe","Accent Challenge 🎭 — say a romantic line differently","Emoji Guess ❤️ — guess the feeling","Compliment Relay 💕 — alternate compliments","Future Home Challenge 🏠 — design your dream home","Travel Challenge ✈️ — choose three dream places","Playlist Challenge 🎵 — pick a song for each other","Secret Signal 🤫 — create a private signal","Photo Pose Battle 📸 — cutest pose wins","Sweet Promise Challenge 💍 — make one promise","Rapid Fire Love ❤️ — five questions each","Memory Date Challenge 📅 — remember a date","Handwriting Guess ✍️ — guess who wrote it","Silly Face Challenge 😂 — hold a silly face","Romantic Caption Challenge 📱 — caption a photo","Two Truths One Love 💖 — share two truths and one love fact","Compliment Without Words 😊 — communicate appreciation silently","Couple Quiz Master 🧠 — ask five questions","Mini Picnic Plan 🧺 — plan a picnic in one minute","Dream Date Budget 💰 — plan a date with a small budget","Team Puzzle 🧩 — solve a simple puzzle together","Kind Words Challenge 💌 — no negative words for 10 minutes","Gratitude Challenge 🙏 — name five things","Matching Emoji Challenge 😍 — choose same emoji","Love Song Guess 🎶 — hum and guess","Photo Memory Talk 📸 — tell story behind a photo","One Minute Appreciation ❤️ — continuous appreciation for one minute"};}
    static String[] makePunishments(){return new String[]{"Give 5 sweet compliments. 💕","Do a 10-second funny dance. 💃","Imitate a favorite dialogue. 🤣","Give a 20-second hug. 🤗","Say one cute apology. 😘","Make one sweet promise. ❤️","Send a romantic sticker. 💌","Do a silly pose. 📸","Say 3 reasons you love them. 🥰","Sing one funny line. 🎵","Make a heart with your hands. ❤️","Give a high five. 🙌","Tell one funny memory. 😂","Use a cute nickname for the next round. 😍","Plan tomorrow's mini date. 📅","Say thank you three times. 💖","Make your best puppy face. 🐶","Do a tiny celebration dance. 🎉","Give a genuine compliment about character. ✨","Recreate a cute selfie pose. 📸","Tell a one-line love story. 📖","Say one thing you admire. 🥰","Make your partner laugh. 😂","Hold hands for 30 seconds. 🤝","Say one future dream. 🌟","Write a tiny love note. ✍️","Do a dramatic romantic movie pose. 🎬","Choose the next song. 🎵","Share the last snack bite. 🍫","Say I choose you today. ❤️","Create a new couple nickname. 😘","Give a forehead kiss if comfortable. 💕","Tell your sweetest memory. 💖","Make a funny face for 10 seconds. 😂","Give a warm hug. 🤗","Say a romantic line in a funny voice. 🎭","Take a matching photo. 📸","Do synchronized claps. 👏","Tell them one thing you appreciate. 🥹","Plan a surprise for next week. 🎁","Say one thing you will improve. 🌱","Make a mini heart drawing. 💗","Give a victory pose. 🏆","Tell a joke. 🤣","Choose a dream destination. ✈️","Say one gratitude. 🙏","Compliment their smile. 😊","Compliment their kindness. ❤️","Compliment their strength. 💪","End with a couple selfie. 📸"};}
    static String[] makeMessages(){return new String[]{"You are my favorite person in the whole world. ❤️","Every moment with you is special.","My heart always chooses you. 💖","Life feels more beautiful with you.","You are my today and all my tomorrows. ❤️","Ordinary moments become beautiful memories with you.","I want to walk beside you wherever life goes. 🥰","You make my heart smile every day. 💕","Our story is my favorite love story.","Together is my favorite place. ❤️","You make simple days feel magical. ✨","I am grateful for every laugh we share. 🥰","Your smile is my favorite notification. 📱❤️","I would choose you again and again.","Home feels like a person, and that person is you. 🏠❤️","Thank you for being my safe place.","My favorite plan is any plan with you.","You make my world softer and brighter. 💖","Our little memories mean everything to me.","I love the way we understand each other.","You are the best part of my ordinary days.","Even silence feels special with you.","I will keep choosing us. ❤️","You are my favorite hello and hardest goodbye.","Your happiness matters to my heart.","Our love grows in the little things.","I am lucky to have this story with you.","One day, our memories will be our favorite treasure.","You make me want to be better every day.","Forever sounds beautiful when it includes you.","Your laugh is one of my favorite sounds.","I love being your person.","You are the reason some days feel extra special.","I want more sunsets, conversations and memories with you.","Our bond is my favorite adventure.","Thank you for every little act of care.","You are precious to me beyond words.","My heart feels calm when I am with you.","I love our silly moments the most.","You make love feel like friendship and home.","No perfect day is needed; I just need you.","You are my favorite chapter and my favorite future.","I smile whenever I think of us.","Let's keep making memories worth retelling.","You are my forever teammate. 🏆❤️","I love the person I am when I am with you.","Our story deserves many more beautiful chapters.","I choose patience, kindness and us.","You are loved, today and every day.","Us against every ordinary day. ❤️"};}
    static String[] makeBirthday() {
        return new String[]{
            "Happy Birthday Chinna Papa ❣️ May your smile shine brighter than every candle today. 🎂❤️",
            "Happy Birthday, my love! May this year bring you beautiful surprises, peace and endless happiness. 🎁💕",
            "Wishing you a birthday filled with hugs, laughter, sweet memories and all the love you deserve. 🥰🎂",
            "Happy Birthday to the one who makes ordinary moments feel magical. Keep smiling always. ✨❤️",
            "May every birthday candle carry one more dream toward reality. Happy Birthday, beautiful soul! 🕯️💖",
            "Happy Birthday! Life is sweeter, brighter and more special with you in it. 🎂🌸",
            "Today is all about celebrating you and the happiness you bring into my life. Happy Birthday! ❤️🎉",
            "May your new year of life be full of adventures, success, love and unforgettable moments. 🥳💫",
            "Happy Birthday, sweetheart! You deserve a day as wonderful and lovely as your heart. 💕🎂",
            "Another beautiful year begins. May every chapter ahead be happier than the last. Happy Birthday! 📖❤️",
            "Sending you a pocket full of wishes, a heart full of love and the biggest birthday hug. 🤗🎁",
            "Happy Birthday! May your dreams grow bigger, your worries grow smaller and your smile never fade. 🌟❤️",
            "To your special day: more laughter, more love, more memories and countless reasons to smile. 🎂💗",
            "Happy Birthday to my favorite person! May today become one of your sweetest memories. 🥰🎉",
            "May happiness follow you today, tomorrow and throughout the whole year. Happy Birthday! 🌷❤️",
            "Happy Birthday! If wishes could be hugs, you would be wrapped in a thousand of them today. 🤗💕",
            "Here is to another year of beautiful moments, brave dreams and a heart full of happiness. Cheers! 🎂✨",
            "Happy Birthday, love! Keep being the amazing person who makes every day brighter. 💖🎈",
            "May your birthday sparkle with joy and your year sparkle with success. Happy Birthday! ✨🎂",
            "Today we celebrate not just your birthday, but the beautiful person you are. Happy Birthday! ❤️🥳",
            "Happy Birthday! May every little wish in your heart find its way to you this year. 🌈💗",
            "Wishing you warm hugs, happy tears, crazy laughter and memories you will treasure forever. 🎁❤️",
            "Happy Birthday, my sunshine! May your day be soft, sweet, joyful and full of love. ☀️💖",
            "May this birthday open the door to your happiest and most exciting chapter yet. 🎂🚪✨",
            "Happy Birthday! You make life more colorful just by being you. Never stop shining. 🌸❤️",
            "A new age, a new chapter and a hundred new reasons to smile. Happy Birthday! 🎉💕",
            "May your special day be filled with your favorite people, favorite moments and favorite smiles. 🎂🥰",
            "Happy Birthday! Sending you love today and every day, with a promise to make more beautiful memories together. ❤️🎁",
            "May every sunrise of this new year bring hope, every sunset bring peace, and every day bring happiness. 🌅💗",
            "Happy Birthday! Your presence is a gift, your smile is a treasure, and your happiness means everything. 🎂💎❤️",
            "Wishing you a birthday full of magic, laughter and tiny moments that become lifelong memories. ✨🥳",
            "Happy Birthday, darling! May your heart stay young, your dreams stay alive and your smile stay unforgettable. 💕🎂",
            "May success surprise you, happiness find you and love surround you this entire year. Happy Birthday! 🌟❤️",
            "Happy Birthday! Today deserves extra cake, extra hugs and extra reasons to celebrate you. 🎂🤗🎉",
            "One more beautiful year, one more collection of memories. Happy Birthday and keep shining! ❤️✨"
        };
    }
    static String[] makeAnniversary() {
        return new String[]{
            "Happy Anniversary! Another beautiful chapter of our love story begins today. 💍❤️",
            "Every moment with you is a memory worth keeping. Happy Anniversary, my love! 🥰💕",
            "Happy Anniversary! Here is to the laughter, little fights, big hugs and endless love. ❤️🥂",
            "One journey, two hearts and countless beautiful memories. Happy Anniversary! 💖✨",
            "Happy Anniversary to my favorite person and my favorite forever. 💍💕",
            "Another year together, another thousand reasons to fall in love with you. ❤️🌹",
            "Happy Anniversary! May our bond grow stronger, sweeter and happier with every passing year. 🥰💗",
            "From our first memory to this moment, every chapter with you is special. Happy Anniversary! 📖❤️",
            "Happy Anniversary! Thank you for making ordinary days feel like our own little love story. 💕✨",
            "Here is to all the memories we made and all the beautiful ones still waiting for us. 🥂❤️",
            "Happy Anniversary, sweetheart! I would choose you again in every lifetime. 💍🥰",
            "Our love is not about being perfect; it is about choosing each other every day. Happy Anniversary! ❤️",
            "Happy Anniversary! May our smiles stay shared, our hands stay held and our hearts stay close. 🤝💗",
            "Another year of us, another reason to celebrate the most beautiful partnership. Happy Anniversary! 🎉💕",
            "Happy Anniversary to the person who makes my world warmer, happier and more complete. ❤️🌸",
            "Every little moment with you has become a big part of my happiness. Happy Anniversary! 🥰💍",
            "Happy Anniversary! May we keep collecting adventures, inside jokes and unforgettable memories. ✨❤️",
            "To forever, one day at a time. Happy Anniversary, my love! 💕♾️",
            "Happy Anniversary! Our story keeps getting better because you are in every chapter. 📖💖",
            "One heart, one journey, countless memories. Happy Anniversary to us! ❤️🥂",
            "Happy Anniversary! Thank you for being my comfort, my laughter and my favorite person. 🥰💕",
            "May every anniversary remind us how lucky we are to have found each other. Happy Anniversary! 💍✨",
            "Happy Anniversary! More love, more laughter, more trips and many more years together. ❤️🌍",
            "Every year with you feels like another beautiful gift. Happy Anniversary! 🎁💗",
            "Happy Anniversary, my love! Our little moments are my biggest treasures. 💎❤️",
            "May our love always have more memories to make than stories to tell. Happy Anniversary! 🥰📸",
            "Happy Anniversary! Still you, still me, still us—and I would not change a thing. ❤️💕",
            "To the hugs that heal, the laughs that stay and the love that grows. Happy Anniversary! 🤗💖",
            "Happy Anniversary! May our future be even more beautiful than everything we have already shared. 🌅❤️",
            "Another year of choosing each other, supporting each other and loving each other. Happy Anniversary! 💍🥰",
            "Happy Anniversary! Our journey is my favorite journey, and I cannot wait for the next chapter. 🚶‍♂️❤️",
            "Two people, one beautiful team. Happy Anniversary to the best partner in the world! 🏆💕",
            "Happy Anniversary! May our love stay playful, peaceful, passionate and forever young. ❤️✨",
            "Every smile, every hug and every memory brought us here. Happy Anniversary, my love! 🌹💗",
            "Happy Anniversary! Here is to our past with gratitude, our present with love and our future with hope. ❤️♾️"
        };
    }
}
