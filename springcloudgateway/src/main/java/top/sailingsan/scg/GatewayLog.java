package top.sailingsan.scg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangqingpeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayLog {

    private String method;
    private String uri;

    private String reqHeaders;
    private String reqContentType;
    private String reqBody;

    private String respBody;
    private String respStatus;

    private String routeId;
    private String routeUri;
    private String remoteAddress;
    private String localeAddress;

    private Long serverStartTime;
    private Long serverEndTime;
    private Long clientStartTime;
    private Long clientEndTime;

    public Long getServerExecuteTime() {
        if (serverEndTime != null && serverStartTime != null) {
            return serverEndTime - serverStartTime;
        } else {
            return null;
        }
    }

    public Long getClientExecuteTime() {
        if (clientEndTime != null && clientStartTime != null) {
            return clientEndTime - clientStartTime;
        } else {
            return null;
        }
    }

}
