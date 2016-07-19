package com.wy.gj.web.rest;

import com.wy.gj.CgApp;
import com.wy.gj.domain.News;
import com.wy.gj.repository.NewsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the NewsResource REST controller.
 *
 * @see NewsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CgApp.class)
@WebAppConfiguration
@IntegrationTest
public class NewsResourceIntTest {


    private static final Long DEFAULT_NEWS_ID = 1L;
    private static final Long UPDATED_NEWS_ID = 2L;
    private static final String DEFAULT_NEWS_NAME = "AAAAA";
    private static final String UPDATED_NEWS_NAME = "BBBBB";
    private static final String DEFAULT_NEWS_TYPE = "AAAAA";
    private static final String UPDATED_NEWS_TYPE = "BBBBB";
    private static final String DEFAULT_NEWS_CONTENT = "AAAAA";
    private static final String UPDATED_NEWS_CONTENT = "BBBBB";
    private static final String DEFAULT_RECEIVER = "AAAAA";
    private static final String UPDATED_RECEIVER = "BBBBB";
    private static final String DEFAULT_SEND_TIME = "AAAAA";
    private static final String UPDATED_SEND_TIME = "BBBBB";

    @Inject
    private NewsRepository newsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNewsMockMvc;

    private News news;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NewsResource newsResource = new NewsResource();
        ReflectionTestUtils.setField(newsResource, "newsRepository", newsRepository);
        this.restNewsMockMvc = MockMvcBuilders.standaloneSetup(newsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        news = new News();
        news.setNewsId(DEFAULT_NEWS_ID);
        news.setNewsName(DEFAULT_NEWS_NAME);
        news.setNewsType(DEFAULT_NEWS_TYPE);
        news.setNewsContent(DEFAULT_NEWS_CONTENT);
        news.setReceiver(DEFAULT_RECEIVER);
        news.setSendTime(DEFAULT_SEND_TIME);
    }

    @Test
    @Transactional
    public void createNews() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // Create the News

        restNewsMockMvc.perform(post("/api/news")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(news)))
                .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> news = newsRepository.findAll();
        assertThat(news).hasSize(databaseSizeBeforeCreate + 1);
        News testNews = news.get(news.size() - 1);
        assertThat(testNews.getNewsId()).isEqualTo(DEFAULT_NEWS_ID);
        assertThat(testNews.getNewsName()).isEqualTo(DEFAULT_NEWS_NAME);
        assertThat(testNews.getNewsType()).isEqualTo(DEFAULT_NEWS_TYPE);
        assertThat(testNews.getNewsContent()).isEqualTo(DEFAULT_NEWS_CONTENT);
        assertThat(testNews.getReceiver()).isEqualTo(DEFAULT_RECEIVER);
        assertThat(testNews.getSendTime()).isEqualTo(DEFAULT_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the news
        restNewsMockMvc.perform(get("/api/news?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
                .andExpect(jsonPath("$.[*].newsId").value(hasItem(DEFAULT_NEWS_ID.intValue())))
                .andExpect(jsonPath("$.[*].newsName").value(hasItem(DEFAULT_NEWS_NAME.toString())))
                .andExpect(jsonPath("$.[*].newsType").value(hasItem(DEFAULT_NEWS_TYPE.toString())))
                .andExpect(jsonPath("$.[*].newsContent").value(hasItem(DEFAULT_NEWS_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())))
                .andExpect(jsonPath("$.[*].sendTime").value(hasItem(DEFAULT_SEND_TIME.toString())));
    }

    @Test
    @Transactional
    public void getNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", news.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(news.getId().intValue()))
            .andExpect(jsonPath("$.newsId").value(DEFAULT_NEWS_ID.intValue()))
            .andExpect(jsonPath("$.newsName").value(DEFAULT_NEWS_NAME.toString()))
            .andExpect(jsonPath("$.newsType").value(DEFAULT_NEWS_TYPE.toString()))
            .andExpect(jsonPath("$.newsContent").value(DEFAULT_NEWS_CONTENT.toString()))
            .andExpect(jsonPath("$.receiver").value(DEFAULT_RECEIVER.toString()))
            .andExpect(jsonPath("$.sendTime").value(DEFAULT_SEND_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNews() throws Exception {
        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news
        News updatedNews = new News();
        updatedNews.setId(news.getId());
        updatedNews.setNewsId(UPDATED_NEWS_ID);
        updatedNews.setNewsName(UPDATED_NEWS_NAME);
        updatedNews.setNewsType(UPDATED_NEWS_TYPE);
        updatedNews.setNewsContent(UPDATED_NEWS_CONTENT);
        updatedNews.setReceiver(UPDATED_RECEIVER);
        updatedNews.setSendTime(UPDATED_SEND_TIME);

        restNewsMockMvc.perform(put("/api/news")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedNews)))
                .andExpect(status().isOk());

        // Validate the News in the database
        List<News> news = newsRepository.findAll();
        assertThat(news).hasSize(databaseSizeBeforeUpdate);
        News testNews = news.get(news.size() - 1);
        assertThat(testNews.getNewsId()).isEqualTo(UPDATED_NEWS_ID);
        assertThat(testNews.getNewsName()).isEqualTo(UPDATED_NEWS_NAME);
        assertThat(testNews.getNewsType()).isEqualTo(UPDATED_NEWS_TYPE);
        assertThat(testNews.getNewsContent()).isEqualTo(UPDATED_NEWS_CONTENT);
        assertThat(testNews.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testNews.getSendTime()).isEqualTo(UPDATED_SEND_TIME);
    }

    @Test
    @Transactional
    public void deleteNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);
        int databaseSizeBeforeDelete = newsRepository.findAll().size();

        // Get the news
        restNewsMockMvc.perform(delete("/api/news/{id}", news.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<News> news = newsRepository.findAll();
        assertThat(news).hasSize(databaseSizeBeforeDelete - 1);
    }
}
