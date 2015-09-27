package com.psk.pms.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.psk.pms.model.Indent;

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
        int noOfRows;

        noOfRows = jdbcTemplate.queryForObject(NOOFFIELDDESCASSOCIATEDTOPROJECT, Integer.class,
                (Integer) projectId);
        LOGGER.info("method = isFieldDescriptionDetailsExistsForProject , isDataPresent :" + (noOfRows != 0));
        if (noOfRows != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isFieldDescriptionDetailsExistsForSubProject(int subProjectId) {
        int noOfRows;
        noOfRows = jdbcTemplate.queryForObject(
                NOOFFIELDDESCASSOCIATEDTOSUBPROJECT, Integer.class,
                (Integer) subProjectId);
        LOGGER.info("method = isFieldDescriptionDetailsExistsForSubProject , isDataPresent :" + (noOfRows != 0));
        if (noOfRows != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void createFieldDescription(int projectId, int subProjectId) {
        LOGGER.info("method = createFieldDescription, creating field description,for projectId :"
                + projectId + " , subProjectId:" + subProjectId);
        if (0 == subProjectId) {
            jdbcTemplate.update(DELETEPROJECTFIELDDESCRIPTION, projectId);
            jdbcTemplate.update(DELETEPROJECTFIELDDESCRIPTIONITEM, projectId);
            jdbcTemplate.update(CREATEPROJECTFIELDDESCRIPTION, projectId);
            jdbcTemplate.update(CREATEPROJECTFIELDDESCRIPTIONITEM, projectId);
        } else {
            jdbcTemplate.update(DELETESUBPROJECTFIELDDESCRIPTION, subProjectId);
            jdbcTemplate.update(DELETESUBPROJECTFIELDDESCRIPTIONITEM, subProjectId);
            jdbcTemplate.update(CREATESUBPROJECTFIELDDESCRIPTION, subProjectId);
            jdbcTemplate.update(CREATESUBPROJECTFIELDDESCRIPTIONITEM, subProjectId);
        }
    }

	@Override
	public boolean saveIndentDescription(Indent indent) {
		// TODO Auto-generated method stub
		return false;
	}
}
