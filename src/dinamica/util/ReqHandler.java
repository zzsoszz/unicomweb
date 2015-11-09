package dinamica.util;

import java.util.Date;

import com.tdt.unicom.serverforsgip.dao.MTReq;

public interface ReqHandler {
	public void doAny(MTReq req) throws Exception;
}
