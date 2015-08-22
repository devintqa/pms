package com.psk.pms.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.psk.pms.dao.PmsMasterQuery.*;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
public class FieldDescriptionDAOImpl implements FieldDescriptionDAO {

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(FieldDescriptionDAOImpl.class);

    @Override
    public boolean isFieldDescriptionDetailsExistsForProject(int projectId) {
        int noOfrows = 0;

        noOfrows = jdbcTemplate.queryForObject(NOOFFIELDDESCASSOCIATEDTOPROJECT, Integer.class,
                new Object[]{
                        (Integer) projectId
                });
        LOGGER.info("method = isProjectDescriptionDetailsExistsForProject , isDataPresent :" + (noOfrows != 0));
        if (noOfrows != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isFieldDescriptionDetailsExistsForSubProject(int subProjectId) {
        int noOfrows = 0;

        noOfrows = jdbcTemplate.queryForObject(
                NOOFFIELDDESCASSOCIATEDTOSUBPROJECT, Integer.class,
                new Object[]{
                        (Integer) subProjectId
                });
        LOGGER.info("method = isProjectDescriptionDetailsExistsForSubProject , isDataPresent :" + (noOfrows != 0));
        if (noOfrows != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void createFieldDescription(int projectId, int subProjectId) {
        if (0 == subProjectId) {
            jdbcTemplate.update(DELETEPROJECTFIELDDESCRIPTION,new Object[]{projectId});
            jdbcTemplate.update(CREATEPROJECTFIELDDESCRIPTION, new Object[]{projectId});
        } else {
            jdbcTemplate.update(DELETESUBPROJECTFIELDDESCRIPTION,new Object[]{subProjectId});
            jdbcTemplate.update(CREATESUBPROJECTFIELDDESCRIPTION, new Object[]{subProjectId});
        }
        }
}
