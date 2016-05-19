package capstone.nanodegree.nemesisdev.com.hiitit.ui.main;

/**
 * Created by Scott on 5/3/2016.
 */
public class MainMenuPresenterImpl implements MainMenuPresenter{

    private MainMenuView mView;

    public MainMenuPresenterImpl(MainMenuView view) {
        mView = view;
    }

    @Override
    public void onMenuItemSelected(int id) {
        mView.openSelectedMenuActivity(id);
    }
}
