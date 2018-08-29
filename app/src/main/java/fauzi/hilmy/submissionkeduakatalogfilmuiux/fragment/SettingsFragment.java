package fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    //    @BindView(R.id.language_setting)
//    TextView languageSetting;
    @BindView(R.id.card_language)
    CardView cardLanguage;
    Unbinder unbinder;

//    CharSequence[] language = {" English ", " Indonesia "};
//    AlertDialog alertDialog;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle(getResources().getString(R.string.settings));

//        if (LocaleHelper.getLanguage(getActivity()).equalsIgnoreCase("en")) {
//            languageSetting.setText("English");
//        } else {
//            languageSetting.setText("Indonesia");
//        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.card_language)
    public void onViewClicked() {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(intent);
//        AlertLanguage();
    }

//    private void AlertLanguage() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(getResources().getString(R.string.selectLanguage));
//
//        builder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Context context;
//                Resources resources;
//                switch (which) {
//                    case 0:
//                        context = LocaleHelper.setLocale(getActivity(), "en");
//                        resources = context.getResources();
//                        languageSetting.setText(resources.getString(R.string.set_english));
//                        break;
//                    case 1:
//                        context = LocaleHelper.setLocale(getActivity(), "in-rID");
//                        resources = context.getResources();
//                        languageSetting.setText(resources.getString(R.string.set_english));
//                        break;
//                }
//                alertDialog.dismiss();
//            }
//        });
//        alertDialog = builder.create();
//        alertDialog.show();
//    }
}
