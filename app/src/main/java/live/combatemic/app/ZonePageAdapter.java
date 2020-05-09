package live.combatemic.app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ZonePageAdapter  extends FragmentPagerAdapter {


    ZonePageAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        if (position == 0) {
            return ZoneListFragment.newInstance(position, "Red");
        } else if (position == 1) {
            return ZoneListFragment.newInstance(position, "Green");
        } else {
            return ZoneListFragment.newInstance(position, "Orange");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Red";
            case 1:
                return "Green";
            case 2:
                return "Orange";
        }
        return null;
    }
}

