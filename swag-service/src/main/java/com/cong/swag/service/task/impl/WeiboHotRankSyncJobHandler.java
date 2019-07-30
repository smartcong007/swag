package com.cong.swag.service.task.impl;

import static com.cong.selenium.SeleniumBase.getWebElement;
import static com.cong.selenium.SeleniumBase.openPage;
import static com.cong.selenium.SeleniumBase.quitDriver;

import com.cong.swag.common.VO.WeiboHotRankItemVO;
import com.cong.swag.common.task.JobModel;
import com.cong.swag.dao.WeiboHotRankDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-25
 */
@Service("weiboHotRankDataStorageJobHandler")
public class WeiboHotRankSyncJobHandler implements JobHandler {
    
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiboHotRankSyncJobHandler.class);

    @Autowired
    WeiboHotRankDao weiboHotRankDao;

    @Override
    public void handleJob(JobModel jobModel) {
        LOGGER.info("开始同步微博热搜...");
        List<WeiboHotRankItemVO> rankItemVOS = new ArrayList<>(50);
        openPage("https://s.weibo.com/top/summary",
            webDriver -> webDriver.findElement(By.id("pl_top_realtimehot")) != null, 15);
        WebElement tBody = getWebElement(By.xpath("//div[@id='pl_top_realtimehot']/table/tbody"));
        List<WebElement> trs = tBody.findElements(By.tagName("tr"));
        trs.remove(0);
        for (WebElement tr : trs) {
            WeiboHotRankItemVO rankItemVO = new WeiboHotRankItemVO();

            List<WebElement> tds = tr.findElements(By.tagName("td"));
            WebElement rankNum = tds.get(0);
            rankItemVO.setId(Integer.valueOf(rankNum.getText()));

            WebElement title = tds.get(1);
            WebElement hr = title.findElement(By.tagName("a"));
            WebElement sp = title.findElement(By.tagName("span"));

            WebElement tag = tds.get(2);
            String tagVal = tag.getText();
            if (StringUtils.isNotEmpty(tagVal)) {
                rankItemVO.setLabel(tagVal);
            }
            rankItemVO.setTitle(hr.getText());
            rankItemVO.setLink(hr.getAttribute("href"));
            rankItemVO.setHits(Long.valueOf(sp.getText()));
            rankItemVOS.add(rankItemVO);
        }
        quitDriver();
        weiboHotRankDao.deleteAll();
        weiboHotRankDao.insert(rankItemVOS);
    }
}
