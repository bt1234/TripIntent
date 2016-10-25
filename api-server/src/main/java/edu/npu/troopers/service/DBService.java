package edu.npu.troopers.service;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBService {

  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParamJdbcTemplate;

  @Autowired
  public DBService(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }
  
  public JdbcTemplate getJdbcTemplate(){
    return this.jdbcTemplate;
  }
  
  public NamedParameterJdbcTemplate getNamedParamJdbcTemplate(){
    return this.namedParamJdbcTemplate;
  }
  
  public Map<String, Object> queryForObject(String sql, Object... params){
    return this.jdbcTemplate.queryForMap(sql, params);
  }
  
  public Map<String, Object> queryForObject(String sql, Map<String, Object> namedParams){
    return this.namedParamJdbcTemplate.queryForMap(sql, namedParams);
  }
  
  public <T> T queryForObject(String sql, RowMapper<T> mapper){
    return this.jdbcTemplate.queryForObject(sql, mapper);
  }
  
  public <T> T queryForObject(String sql, RowMapper<T> mapper, Object... params){
    return this.jdbcTemplate.queryForObject(sql, mapper, params);
  }
  
  public <T> T queryForObject(String sql, RowMapper<T> mapper, Map<String, Object> namedParams){
    return this.namedParamJdbcTemplate.queryForObject(sql, namedParams, mapper);
  }
  
  public List<Map<String, Object>> queryForList(String sql, Object... params){
    return this.jdbcTemplate.queryForList(sql, params);
  }
  
  public List<Map<String, Object>> queryForList(String sql, Map<String, Object> namedParams){
    return this.namedParamJdbcTemplate.queryForList(sql, namedParams);
  }
  
  public <T> List<T> queryForList(String sql, RowMapper<T> mapper){
    return this.jdbcTemplate.query(sql, mapper);
  }
  
  public <T> List<T> queryForList(String sql, RowMapper<T> mapper, Object... params){
    return this.jdbcTemplate.query(sql, mapper, params);
  }
  
  public <T> List<T> queryForList(String sql, RowMapper<T> mapper, Map<String, Object> namedParams){
    return this.namedParamJdbcTemplate.query(sql, namedParams, mapper);
  }
  
  public void update(String sql, Object... params){
    this.jdbcTemplate.update(sql, params);
  }
  
  public void update(String sql, Map<String, Object> namedParams){
    this.namedParamJdbcTemplate.update(sql, namedParams);
  }
  
}
