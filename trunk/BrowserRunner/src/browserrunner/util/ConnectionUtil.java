package browserrunner.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;

public class ConnectionUtil {

	private static int cacheTimeout = 600000;//10 minutes by default

	private static HashMap<String,Boolean> cacheHost =new  HashMap<String,Boolean> ();
	private static HashMap<String,Date> cacheTime = new HashMap<String,Date>();

	/**
	 * Reference to http://techdive.in/java/java-ping-host-or-ip-address
	 *
	 * We do some internal cache result 10 minutes by default for performance issue,
	 * you could setup ignore cache to skip cache.
	 * @param timeout (mileseconds)
	 * @return
	 */
	public static boolean isHostAlive(String host,int timeout , boolean ignoreCache){
		if(!ignoreCache && isCached(host) ){
			return cacheHost.get(host);
		}

		boolean result = checkHostAlive(host,timeout);
		cacheHost.put(host, result);
		cacheTime.put(host, new Date());
		return result;
	}

	private static boolean isCached(String hostName){
		boolean contains = cacheHost.containsKey(hostName);

		if(!contains ){
			return false;
		}
		long diff = new Date().getTime() - cacheTime.get(hostName).getTime();

		return (diff < cacheTimeout ) ;
	}

	private static boolean checkHostAlive(String host,int timeout){

        try
        {
            InetAddress inet = InetAddress.getByName(host);
            return inet.isReachable(timeout); //Timeout =  mileseconds

        }
        catch (UnknownHostException e)
        {
        	return false;
        }
        catch (IOException e)
        {
        	//TODO review this case.
        }
        return false;
}
}
