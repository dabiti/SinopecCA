package com.unitop.config;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.andromda.core.common.ResourceUtils;
import org.andromda.core.common.XmlObjectFactory;


public class PrivilegeConfig {



	public static boolean CheckPrivilege(String popedom, int pos) {
		String ReturnValue = "";
		try {
			ReturnValue = popedom.substring(pos - 1, pos);
			if (ReturnValue != null && ReturnValue.equals("1")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
