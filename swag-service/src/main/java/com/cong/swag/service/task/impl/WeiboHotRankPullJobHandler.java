package com.cong.swag.service.task.impl;

import static com.cong.selenium.SeleniumBase.getWebElement;
import static com.cong.selenium.SeleniumBase.openPage;
import static com.cong.selenium.SeleniumBase.quitDriver;

import com.alibaba.fastjson.JSONObject;
import com.cong.swag.common.task.JobModel;
import com.cong.swag.common.util.HttpClientUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-19
 */
@Service("weiboHotRankPullJobHandler")
public class WeiboHotRankPullJobHandler implements JobHandler {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiboHotRankPullJobHandler.class);

    @Override
    public void handleJob(JobModel jobModel) {
        LOGGER.info("开始执行微博热搜推送任务...");

        JSONObject msg = new JSONObject();
        msg.put("msgtype", "markdown");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#### 微博热搜\n");
        openPage("https://s.weibo.com/top/summary",
            webDriver -> webDriver.findElement(By.id("pl_top_realtimehot")) != null, 15);
        WebElement tBody = getWebElement(By.xpath("//div[@id='pl_top_realtimehot']/table/tbody"));
        List<WebElement> trs = tBody.findElements(By.tagName("tr"));
        trs = trs.subList(0,20);
        for (WebElement tr : trs) {
            List<WebElement> tds = tr.findElements(By.tagName("td"));
            WebElement rankNum = tds.get(0);
            if (rankNum.getAttribute("class").equals("td-01")) {
                stringBuilder.append("> ");
            } else {
                stringBuilder.append(rankNum.getText() + ". ");
            }

            WebElement title = tds.get(1);
            WebElement hr = title.findElement(By.tagName("a"));

            WebElement tag = tds.get(2);
            String tagVal = tag.getText();
            if (StringUtils.isNotEmpty(tagVal)) {
                String color = null;
                if ("热".equals(tagVal)) {
                    color = "#977648";
                } else if ("荐".equals(tagVal)) {
                    color = "#497984";
                } else if ("新".equals(tagVal)) {
                    color = "#7F373D";
                } else if ("沸".equals(tagVal)) {
                    color = "#845A3A";
                }
                stringBuilder.append(String
                    .format("[%s&#160;&#160;&#160;&#160;&#160;](%s)<font color=\"%s\">%s</font>", hr.getText(),
                        hr.getAttribute("href"), color, tagVal));
            } else {
                stringBuilder.append(String.format("[%s](%s)", hr.getText(), hr.getAttribute("href")));
            }
            stringBuilder.append("\n");
        }
        quitDriver();
        JSONObject markdown = new JSONObject();
        markdown.put("title", "微博热搜");
        markdown.put("text", stringBuilder.toString());
        msg.put("markdown", markdown);
        HttpClientUtil.sendPostRequest(
            "https://oapi.dingtalk.com/robot/send?access_token=13b7b95407f1bb157e9c96f612a7a8ef1c0a67900f4f30c203651d9e963dcc6c",
            JSONObject.toJSONString(msg));

    }
}
