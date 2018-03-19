package com.care.sys.appinterfaces;

import java.util.List;

import com.care.sys.LocRadiusPoiResponse.domain.Cells;
import com.care.sys.LocRadiusPoiResponse.domain.JsonUtil;
import com.care.sys.LocRadiusPoiResponse.domain.LocRadiusPoiResponse;
import com.care.sys.LocRadiusPoiResponse.domain.RemoteUtil;
import com.care.sys.LocRadiusPoiResponse.domain.WI;
import com.care.sys.LocRadiusPoiResponse.domain.WifiList;

public class Example {

	// http://minigps.net/cw?x=1cc-0-6212-2F8C-AC-52EC-28AD-96-6212-2F8B-96-6212-FAD-94-6212-3FB5-93-6212-FAB-93
	// {"ws":[{"s":"xo","r":81,"m":804380873802619826},{"s":"terry","r":69,"m":2018924576320342756},{"s":"TP-LINK_3225EE","r":53,"m":674173120793097686},{"s":"loushangshengyinxiaodian","r":49,"m":44590646795096412}]}
	static public LocRadiusPoiResponse getPOIFromMinigps(List<Cells> cells,
			List<WI> ws, int mt) {
		LocRadiusPoiResponse loc = null;
		String result = null;
		String url = "http://minigps.net/cw?p=1&needaddress=0&mt=" + mt;
		String x = getXParameter(cells);
		String w = getWParameter(ws);
		if (x != null) {
			url = url + "&x=" + x;
		}
		if (w != null) {
			result = RemoteUtil.request(url, "POST",
					"application/json;charset=utf-8", w);
		} else {
			result = RemoteUtil.request(url, "GET",
					"application/json;charset=utf-8", null);
		}
		if (result != null) {
			System.out.print(result);
			loc = (LocRadiusPoiResponse) JsonUtil.fromJson(
					LocRadiusPoiResponse.class, result);
		}
		return loc;
	}

	static private String getXParameter(List<Cells> cells) {
		String x = null;
		if (cells != null && cells.size() > 0) {
			// mcc-mnc
			x = "%x-%x";
			Cells c = cells.get(0);
			x = String.format(x, c.getMcc(), c.getMnc());
			for (int i = 0; i < cells.size(); ++i) {
				c = cells.get(i);
				x += "-%x-%x-%x";
				x = String.format(x, c.getLac(), c.getCellid(), c.getSdb());
			}
		}
		return x;
	}

	static private String getWParameter(List<WI> ws) {
		String w = null;
		if (ws != null && ws.size() > 0) {
			WifiList wlist = new WifiList();
			wlist.setWs(ws);
			w = JsonUtil.toJson(wlist);
		}
		return w;
	}

}
