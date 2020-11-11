package com.example.slurteam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideViewPagerAdapter extends PagerAdapter {

    Context ctx;

    public SlideViewPagerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_screen,container,false);


        ImageView logo=view.findViewById(R.id.logo);

        ImageView ind1=view.findViewById(R.id.ind1);
        ImageView ind2=view.findViewById(R.id.ind2);
        ImageView ind3=view.findViewById(R.id.ind3);


        TextView title=view.findViewById(R.id.title);
        TextView desc=view.findViewById(R.id.desc);

        TextView next=view.findViewById(R.id.next);
        TextView back=view.findViewById(R.id.back);
        TextView finish=view.findViewById(R.id.finish);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideActivity.viewPager.setCurrentItem(position+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideActivity.viewPager.setCurrentItem(position-1);
            }
        });



        switch (position)
        {
            case 0:
                logo.setImageResource(R.drawable.logo1);
                ind1.setImageResource(R.drawable.seleted);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("GTA San Andreas");
                desc.setText("This is random text taking from lorem ipsum tesing puspose");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                finish.setVisibility(View.GONE);
                break;
            case 1:
                logo.setImageResource(R.drawable.logo2);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.seleted);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("Dota 2");
                desc.setText("This is random text taking from lorem ipsum tesing puspose");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                finish.setVisibility(View.GONE);
                break;
            case 2:
                logo.setImageResource(R.drawable.logo3);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.seleted);

                title.setText("PLAYERUNKNOWNBATTLEGROUND");
                desc.setText("This is random text taking from lorem ipsum tesing puspose");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                finish.setVisibility(View.VISIBLE);
                break;






        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,MainActivity.class);
                ctx.startActivity(intent);
            }
        });



        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}

