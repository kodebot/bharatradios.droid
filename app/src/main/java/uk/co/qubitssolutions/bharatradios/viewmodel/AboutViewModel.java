package uk.co.qubitssolutions.bharatradios.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import uk.co.qubitssolutions.bharatradios.R;

public class AboutViewModel {

    public void onContactUs(View view) {
        Intent intent = new Intent(
                Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", view.getContext().getString(R.string.contact_email), null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "RE: bharat radios");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        view.getContext().startActivity(Intent.createChooser(intent, "Send email..."));
    }

    public void onOpenWebsite(View view) {
        String url = view.getContext().getString(R.string.company_website);
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
        websiteIntent.setData(Uri.parse(url));
        view.getContext().startActivity(websiteIntent);
    }
}
