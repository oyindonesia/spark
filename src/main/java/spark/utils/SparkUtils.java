/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spark.utils;

import spark.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Some utility methods
 *
 * @author Per Wendel
 */
public final class SparkUtils {

    public static final String ALL_PATHS = "+/*paths";

    private SparkUtils() {
    }

    public static List<String> convertRouteToList(String route) {
        String[] pathArray = route.split("/");
        List<String> path = new ArrayList<>();
        for (String p : pathArray) {
            if (p.length() > 0) {
                path.add(p);
            }
        }
        return path;
    }

    public static boolean isParam(String routePart) {
        return routePart.startsWith(":");
    }

    public static boolean isSplat(String routePart) {
        return routePart.equals("*");
    }

    public static String getPathName(Request request) {
        String pathIdentifier = request.uri();
        if(pathIdentifier == null) return "";
        Map<String, String> params = request.params();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pathIdentifier = pathIdentifier.replaceFirst(entry.getValue(), entry.getKey());
        }
        pathIdentifier = pathIdentifier.replaceAll("/", "_");
        pathIdentifier = pathIdentifier.replaceAll(":", "");
        return pathIdentifier;
    }
}
