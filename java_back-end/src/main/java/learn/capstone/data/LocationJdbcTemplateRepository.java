package learn.capstone.data;

import learn.capstone.data.mappers.LocationMapper;
import learn.capstone.models.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LocationJdbcTemplateRepository implements LocationRepository {

    private final JdbcTemplate jdbcTemplate;

    public LocationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Location> findAll() {
        final String sql = "select location_id, description, latitude, longitude "
                + "from location limit 1000;";
        return jdbcTemplate.query(sql, new LocationMapper());
    }

    @Override
    @Transactional
    public Location findById(int locationId) {
        final String sql = "select location_id, description, latitude, longitude  "
                + "from location "
                + "where location_id = ?;";

        Location location = jdbcTemplate.query(sql, new LocationMapper(), locationId).stream()
                .findFirst().orElse(null);

        return location;
    }

    @Override
    public Location findByDescription(String description) {
        final String sql = "select location_id, description, latitude, longitude "
                + "from location "
                + "where description = ?;";

        Location location = jdbcTemplate.query(sql, new LocationMapper(), description).stream()
                .findFirst().orElse(null);

        return location;
    }

    @Override
    public Location add(Location location) {
        final String sql = "insert into location (description,latitude,longitude) "
                + " value (?,?,?);";

       KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, location.getDescription());
            ps.setFloat(2, location.getLatitude());
            ps.setFloat(3, location.getLongitude());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        location.setLocationId(keyHolder.getKey().intValue());
        return location;
    }

    @Override
    public boolean update(Location location) {

        final String sql = "update location set "
                + "description = ?, "
                + "latitude = ?, "
                + "longitude = ? "
                + "where location_id = ?;";

        return jdbcTemplate.update(sql,
                location.getDescription(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int locationId) {
        jdbcTemplate.update("delete from vacation_stops where location_id = ?;", locationId);
        return jdbcTemplate.update("delete from location where location_id = ?;", locationId) > 0;
    }
}
