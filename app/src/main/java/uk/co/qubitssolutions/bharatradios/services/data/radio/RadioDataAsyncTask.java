package uk.co.qubitssolutions.bharatradios.services.data.radio;

import android.os.AsyncTask;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.model.Radio;


public class RadioDataAsyncTask extends AsyncTask<Void, Void, List<Radio>> {
    private Callback callback;
    private RadioDataService radioDataService;

    public RadioDataAsyncTask(Callback callback) {
        this.callback = callback;
        radioDataService  = new RadioDataService();
    }

    @Override
    protected List<Radio> doInBackground(Void... params) {
        return radioDataService.getRadios();
    }

    @Override
    protected void onPostExecute(List<Radio> radios) {
        super.onPostExecute(radios);
        callback.run(radios);
    }

    public interface Callback {
        void run(List<Radio> radio);
    }

}
