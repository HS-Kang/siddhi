/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.siddhi.core.event.stream;

import org.wso2.siddhi.core.event.ComplexEvent;

import java.util.Arrays;

import static org.wso2.siddhi.core.util.SiddhiConstants.*;

/**
 * Standard processing event inside Siddhi. StreamEvent will be created
 * from StreamEvent before sending to relevant Queries.
 */
public class StreamEvent implements ComplexEvent {

    protected long timestamp = -1;
    private Object[] beforeWindowData;          //Attributes before window execution
    private Object[] onAfterWindowData;         //Attributes on and after window execution
    protected Object[] outputData;              //Attributes to sent as output
    //    protected boolean isExpired = false;
    protected Type type = Type.CURRENT;
    private StreamEvent next;

    public StreamEvent(int beforeWindowDataSize, int onAfterWindowDataSize, int outputDataSize) {
        if (beforeWindowDataSize > 0) {
            beforeWindowData = new Object[beforeWindowDataSize];
        }
        if (onAfterWindowDataSize > 0) {
            onAfterWindowData = new Object[onAfterWindowDataSize];
        }
        if (outputDataSize > 0) {
            outputData = new Object[outputDataSize];
        }
    }

//    public StreamEvent() {
//        //Do nothing
//    }

    public Object[] getBeforeWindowData() {
        return beforeWindowData;
    }

    public void setBeforeWindowData(Object[] beforeWindowData) {
        this.beforeWindowData = beforeWindowData;
    }

    public Object[] getOnAfterWindowData() {
        return onAfterWindowData;
    }

    public void setOnAfterWindowData(Object[] onAfterWindowData) {
        this.onAfterWindowData = onAfterWindowData;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object[] getOutputData() {
        return outputData;
    }

    public void setOutputData(Object[] outputData) {
        this.outputData = outputData;
    }

//    public boolean isExpired() {
//        return isExpired;
//    }
//
//    public void setExpired(boolean isExpired) {
//        this.isExpired = isExpired;
//    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public StreamEvent getNext() {
        return next;
    }

    public void setNext(ComplexEvent next) {
        this.next = (StreamEvent) next;
    }

    /**
     * @param position int array of 4 elements
     *                 position[0] and position[1] are discarded
     *                 position[2]-BeforeWindowData or OutputData or AfterWindowData, position[3]- which attribute
     * @return
     */
    @Override
    public Object getAttribute(int[] position) {
        switch (position[STREAM_ATTRIBUTE_TYPE_INDEX]) {
            case BEFORE_WINDOW_DATA_INDEX:
                return beforeWindowData[position[STREAM_ATTRIBUTE_INDEX]];
            case OUTPUT_DATA_INDEX:
                return outputData[position[STREAM_ATTRIBUTE_INDEX]];
            case ON_AFTER_WINDOW_DATA_INDEX:
                return onAfterWindowData[position[STREAM_ATTRIBUTE_INDEX]];
            default:
                throw new IllegalStateException("STREAM_ATTRIBUTE_TYPE_INDEX cannot be " +
                        position[STREAM_ATTRIBUTE_TYPE_INDEX]);
        }

    }

    public void setOutputData(Object object, int index) {
        this.outputData[index] = object;
    }

    public void setOnAfterWindowData(Object object, int index) {
        this.onAfterWindowData[index] = object;
    }

    public void setBeforeWindowData(Object object, int index) {
        this.beforeWindowData[index] = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreamEvent)) return false;

        StreamEvent event = (StreamEvent) o;

        if (type != event.type) return false;
        if (timestamp != event.timestamp) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(beforeWindowData, event.beforeWindowData)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(onAfterWindowData, event.onAfterWindowData)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(outputData, event.outputData)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (beforeWindowData != null ? Arrays.hashCode(beforeWindowData) : 0);
        result = 31 * result + (onAfterWindowData != null ? Arrays.hashCode(onAfterWindowData) : 0);
        result = 31 * result + (outputData != null ? Arrays.hashCode(outputData) : 0);
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StreamEvent{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", beforeWindowData=").append(beforeWindowData == null ? "null" : Arrays.asList(beforeWindowData).toString());
        sb.append(", onAfterWindowData=").append(onAfterWindowData == null ? "null" : Arrays.asList(onAfterWindowData).toString());
        sb.append(", outputData=").append(outputData == null ? "null" : Arrays.asList(outputData).toString());
        sb.append(", type=").append(type);
        sb.append(", next=").append(next);
        sb.append('}');
        return sb.toString();
    }
}
