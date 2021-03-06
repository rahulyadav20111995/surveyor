package com.surveyapp.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.surveyapp.Adapters.ViewPagerAdapter;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabEvents;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsCommunity;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsCustomerFeedback;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsDemographics;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsEducation;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsHealthCare;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsHumanResources;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsIndustrySpecific;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsJustForFun;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsMarketResearch;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsMostPopular;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsNonProfit;
import com.surveyapp.Fragments.TemplateSurveyTabs.TabsPolitical;
import com.surveyapp.R;

public class TemplatesActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setTabsWithViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

   private void setTabsWithViewPager(){

       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
       adapter.addFragment(new TabsMostPopular(),getString(R.string.title_tab_most_popular));
       adapter.addFragment(new TabsCommunity(),getString(R.string.title_tab_community));
       adapter.addFragment(new TabsCustomerFeedback(),getString(R.string.title_tab_customer_feedback));
       adapter.addFragment(new TabsDemographics(),getString(R.string.title_tab_demographics));
       adapter.addFragment(new TabsEducation(),getString(R.string.title_tab_education));
       adapter.addFragment(new TabEvents(),getString(R.string.title_tab_events));
       adapter.addFragment(new TabsJustForFun(),getString(R.string.title_tab_just_for_fun));
       adapter.addFragment(new TabsHealthCare(),getString(R.string.title_tab_health_care));
       adapter.addFragment(new TabsIndustrySpecific(),getString(R.string.title_tab_industry_specific));
       adapter.addFragment(new TabsHumanResources(),getString(R.string.title_tab_human_resources));
       adapter.addFragment(new TabsMarketResearch(),getString(R.string.title_tab_market_research));
       adapter.addFragment(new TabsNonProfit(),getString(R.string.title_tab_non_profit));
       adapter.addFragment(new TabsPolitical(),getString(R.string.title_tab_political));

       viewPager.setAdapter(adapter);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
