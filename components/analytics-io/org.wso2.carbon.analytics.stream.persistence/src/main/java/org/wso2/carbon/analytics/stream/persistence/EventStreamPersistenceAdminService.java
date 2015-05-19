/*
* Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.analytics.stream.persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.analytics.dataservice.AnalyticsDataService;
import org.wso2.carbon.analytics.datasource.commons.AnalyticsSchema;
import org.wso2.carbon.analytics.datasource.commons.ColumnDefinition;
import org.wso2.carbon.analytics.datasource.commons.exception.AnalyticsException;
import org.wso2.carbon.analytics.stream.persistence.dto.AnalyticsTable;
import org.wso2.carbon.analytics.stream.persistence.dto.AnalyticsTableColumn;
import org.wso2.carbon.analytics.stream.persistence.internal.ServiceHolder;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.core.AbstractAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent Event Stream Persistence admin operations
 */
public class EventStreamPersistenceAdminService extends AbstractAdmin {

    private static Log log = LogFactory.getLog(EventStreamPersistenceAdminService.class);

    /**
     * This method use to check whether back-end is available for the UI to show analytics table related operation
     *
     * @return true if back-end service available
     */
    public boolean isBackendServicePresent() {
        return true;
    }

    /**
     * This method is use to get Analytics table information for given stream name without considering the version
     *
     * @param streamName
     * @return AnalyticsTable instance with column details
     */
    public AnalyticsTable getAnalyticsTable(String streamName) {
        return new AnalyticsTable();
    }

    /**
     * This method will create a deployable artifact for given AnalyticsTable information.
     *
     * @param analyticsTable
     */
    public void addAnalyticsTable(AnalyticsTable analyticsTable) {
        if (analyticsTable != null) {
            if (analyticsTable.getAnalyticsTableColumns() != null) {
                List<ColumnDefinition> columnDefinitions = new ArrayList<>(analyticsTable.getAnalyticsTableColumns().length);
                List<String> primaryKeys = new ArrayList<>();
                for (AnalyticsTableColumn analyticsTableColumn : analyticsTable.getAnalyticsTableColumns()) {
                    ColumnDefinition columnDefinition = new ColumnDefinition();
                    columnDefinition.setName(analyticsTableColumn.getColumnName());
                    columnDefinition.setType(getColumnType(analyticsTableColumn.getColumnType()));
                    columnDefinition.setIndexed(analyticsTableColumn.isIndexed());
                    columnDefinition.setScoreParam(analyticsTableColumn.isScoreParam());
                    columnDefinitions.add(columnDefinition);
                    if (analyticsTableColumn.isPrimaryKey()) {
                        primaryKeys.add(analyticsTableColumn.getColumnName());
                    }
                }
                AnalyticsDataService analyticsDataService = ServiceHolder.getAnalyticsDataService();
                try {
                    AnalyticsSchema schema = new AnalyticsSchema(columnDefinitions, primaryKeys);
                    analyticsDataService.setTableSchema(getTenantId(), analyticsTable.getTableName(), schema);
                } catch (AnalyticsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getTenantId() {
        return CarbonContext.getThreadLocalCarbonContext().getTenantId();
    }

    private AnalyticsSchema.ColumnType getColumnType(String type) {
        switch (type) {
            case "STRING":
                return AnalyticsSchema.ColumnType.STRING;
            case "INTEGER":
                return AnalyticsSchema.ColumnType.INTEGER;
            case "LONG":
                return AnalyticsSchema.ColumnType.LONG;
            case "BOOLEAN":
                return AnalyticsSchema.ColumnType.BOOLEAN;
            case "FLOAT":
                return AnalyticsSchema.ColumnType.FLOAT;
            case "DOUBLE":
                return AnalyticsSchema.ColumnType.DOUBLE;
            case "FACET":
                return AnalyticsSchema.ColumnType.FACET;
            default:
                return AnalyticsSchema.ColumnType.STRING;
        }
    }
}
