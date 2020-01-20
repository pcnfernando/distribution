/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.siddhi.parser.core.topology.models;

import io.siddhi.parser.core.util.ResourceManagerConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Holder to hold required details of Query Groups in {@link SiddhiTopology}
 */
public class SiddhiQueryGroup {
    private String name;
    private int parallelism;
    private String siddhiApp;
    private Map<String, InputStreamDataHolder> inputStreams;
    private Map<String, OutputStreamDataHolder> outputStreams;
    private List<String> queryList;
    private boolean isReceiverQueryGroup;
    private boolean isStateful = false;
    private boolean messagingSourceAvailable = false;

    public SiddhiQueryGroup(String name, int parallelism) {
        this.name = name;
        this.parallelism = parallelism;
        this.queryList = new ArrayList<>();
        siddhiApp = " ";
        inputStreams = new HashMap<>();
        outputStreams = new HashMap<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParallelism() {
        return parallelism;
    }

    public String getSiddhiApp() {
        //combination of InputStream definitions , OutputStream and queries
        StringBuilder stringBuilder = new StringBuilder("@App:name('${" + ResourceManagerConstants.APP_NAME + "}') \n");

        for (InputStreamDataHolder inputStreamDataHolder : inputStreams.values()) {

            siddhiApp = inputStreamDataHolder.getStreamDefinition();
            if (siddhiApp != null) {
                stringBuilder.append(siddhiApp).append(";\n");
            }
        }

        for (OutputStreamDataHolder outputStreamDataHolder : outputStreams.values()) {
            siddhiApp = outputStreamDataHolder.getStreamDefinition();
            if (siddhiApp != null) {
                stringBuilder.append(siddhiApp).append(";\n");
            }
        }

        for (String aQueryList : queryList) {
            stringBuilder.append(aQueryList).append(";\n");
        }

        siddhiApp = stringBuilder.toString();
        return stringBuilder.toString();
    }

    public void addQuery(String query) {
        queryList.add(query);
    }

    public void addQueryAtFirst(String query){
        queryList.add(0,query);
    }

    public void addOutputStream(String key, OutputStreamDataHolder outputStreamDataHolder) {
        if (outputStreamDataHolder != null) {
            outputStreams.put(key, outputStreamDataHolder);
        }
    }

    public void addInputStreams(Map<String, InputStreamDataHolder> inputStreamDataHolderMap) {
        if (inputStreamDataHolderMap != null) {
            this.inputStreams.putAll(inputStreamDataHolderMap);
        }

    }

    public Map<String, InputStreamDataHolder> getInputStreams() {
        return inputStreams;
    }

    public Map<String, OutputStreamDataHolder> getOutputStreams() {
        return outputStreams;
    }

    public boolean isReceiverQueryGroup() {
        return isReceiverQueryGroup;
    }

    public void setReceiverQueryGroup(boolean receiverQueryGroup) {
        isReceiverQueryGroup = receiverQueryGroup;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public List<String> getQueryList() {
        return queryList;
    }

    public boolean isMessagingSourceAvailable() {
        return messagingSourceAvailable;
    }

    public void setMessagingSourceAvailable(boolean messagingSourceAvailable) {
        this.messagingSourceAvailable = messagingSourceAvailable;
    }

    public boolean isStateful() {
        return isStateful;
    }

    public void setStateful(boolean stateful) {
        isStateful = stateful;
    }
}
