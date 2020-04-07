package live.combatemic.app.Common;

import org.json.JSONArray;
import org.json.JSONException;

public interface ServerCallbackArray {
    void onSuccess(JSONArray result) throws JSONException;
}
