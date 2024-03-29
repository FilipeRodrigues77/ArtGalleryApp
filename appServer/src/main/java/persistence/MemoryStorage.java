package persistence;



import domain.*;
import services.*;
import util.ArtworkUtils;
import util.ExhibitionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a DAO (Data Access Object) implementation for Services using in-memory data.
 * Other implementations can implement the services over e.g. a DBMS (Database Management Systems).
 */
public class MemoryStorage implements ArtistService, ArtworkService, GalleryService, ExhibitionService,NationalityService, RegionService {

    private final String DB_URL  = "jdbc:mysql://localhost:3306/IAProj";
    private final String DB_USER = "nuel", DB_PASSWORD = "testing12345";


    // Artist methods
    @Override
    public List<Artist> getAllArtists() throws ServiceException {

        String commandSQL = "SELECT * FROM Artist";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            return buildArtists(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Artist getArtistByID(int id) throws ServiceException {
        Artist artist;
        String commandSQL = "SELECT * FROM Artist WHERE idArtist = " + id;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            List<Artist> artists = buildArtists(resultSet);
            if (!artists.isEmpty()){
                artist = artists.get(0);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artist;
    }

    @Override
    public List<Artist> getArtistByName(String name) throws ServiceException {

        String commandSql = "SELECT * FROM Artist WHERE nameArtist LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                return buildArtists(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Artist> getArtistsByNationality(String nationality) throws ServiceException {
        String commandSql = "SELECT * FROM Artist WHERE nationalityName = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setString(1, nationality);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtists(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artist> getArtistsByDate(String date, boolean isBirthdate) throws ServiceException {
        String columnName = isBirthdate ? "birthDate" : "deathDate";
        String commandSql = "SELECT * FROM Artist WHERE " + columnName + " = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setString(1, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtists(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artist createArtist(Artist artist) throws ServiceException {

        String commandSql = "INSERT INTO Artist (nameArtist, birthDate, deathDate, biography, " +
                "nationalityName, slug, referenceImage) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, artist.getName());
            preparedStatement.setString(2, artist.getBirthdate());
            preparedStatement.setString(3, artist.getDeathdate());
            preparedStatement.setString(4, artist.getBiography());
            preparedStatement.setString(5, artist.getNationality());
            preparedStatement.setString(6, artist.getSlug());
            preparedStatement.setString(7, artist.getReferenceImage());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    artist.setId(generatedKeys.getInt(1));
                }
            }


            return artist;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artist updateArtist(Artist artist) throws ServiceException {
        String commandSql = "UPDATE artist\n" +
                "SET nameArtist = ?, birthDate = ?, deathDate = ?, biography = ?, nationalityName = ?, slug = ?" +
                ", referenceImage = ? " +
                "WHERE idArtist = " + artist.getId();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, artist.getName());
            preparedStatement.setString(2, artist.getBirthdate());
            preparedStatement.setString(3, artist.getDeathdate());
            preparedStatement.setString(4, artist.getBiography());
            preparedStatement.setString(5, artist.getNationality());
            preparedStatement.setString(6, artist.getSlug());
            preparedStatement.setString(7, artist.getReferenceImage());

            int affectedRows = preparedStatement.executeUpdate();

            return artist;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artist deleteArtist(int id) throws ServiceException {

        String commandSql = "DELETE FROM artist WHERE idArtist = " + id;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                Artist deletedArtist = new Artist();
                deletedArtist.setId(id);
                return deletedArtist;
            }
            else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Artist> buildArtists(ResultSet resultSet) throws SQLException {

        List<Artist> artists = new ArrayList<>();
        while (resultSet.next()) {
            Artist artist = new Artist();
            artist.setId(resultSet.getInt("idArtist"));
            artist.setName(resultSet.getString("nameArtist"));
            artist.setBirthdate(resultSet.getString("birthDate"));
            artist.setDeathdate(resultSet.getString("deathDate"));
            artist.setBiography(resultSet.getString("biography"));
            artist.setNationality(resultSet.getString("nationalityName"));
            artist.setSlug(resultSet.getString("slug"));
            artist.setReferenceImage(resultSet.getString("referenceImage"));

            // add a single artist to the list of artists
            artists.add(artist);
        }

        return artists;
    }


    // Artwork methods--------------------------------------------------------------------------------------------------
    @Override
    public List<Artwork> getAllArtworks() throws ServiceException {

        String commandSQL = "SELECT * FROM Artwork";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {
            return buildArtwork(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Artwork getArtworkByID(int id) throws ServiceException {
        Artwork artwork;
        String commandSQL = "SELECT * FROM Artwork WHERE idArtwork = " + id;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            List<Artwork> artworks = buildArtwork(resultSet);
            if (!artworks.isEmpty()){
                artwork = artworks.get(0);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artwork;
    }

    @Override
    public List<Artwork> getArtworkByName(String name) throws ServiceException {

        String commandSql = "SELECT * FROM Artwork WHERE nameArtwork LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtwork(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artwork> getArtworksByMedium(String medium) throws ServiceException {

        String commandSql = "SELECT * FROM Artwork WHERE mediumArtwork LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setString(1, medium + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtwork(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artwork> getArtworksByCategory(String category) throws ServiceException {
        String commandSql = "SELECT * FROM Artwork WHERE category = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtwork(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artwork> getArtworksByDate(String date) throws ServiceException {
        String commandSql = "SELECT * FROM Artwork WHERE creationDate = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setString(1, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtwork(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artwork> getArtworksByArtist(int artistId) throws ServiceException {

        String commandSql = "SELECT * FROM Artwork WHERE idArtist = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setInt(1, artistId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtwork(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artwork> getArtworksByExhibition(int exhibitionId) throws ServiceException {
        List<Artwork> artworks = new ArrayList<>();
        String commandSql = "SELECT * FROM shows WHERE idExhibition = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
             preparedStatement.setInt(1, exhibitionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    artworks.add(getArtworkByID(resultSet.getInt("idArtwork")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artworks;
    }

    @Override
    public List<Artwork> getArtistsByGallery(int galleryId) throws ServiceException {

        String commandSql = "SELECT * FROM Artwork WHERE idGallery = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setInt(1, galleryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Artwork> artworks = buildArtwork(resultSet);

                if(!artworks.isEmpty()){
                    return artworks;
                }
                else{
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Artwork> getArtworksByPriceRange(double minPrice, double maxPrice) throws ServiceException {

        String commandSql = "SELECT * FROM Artwork WHERE price >= ? AND price <= ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(2, maxPrice);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtwork(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artwork> getArtworksByDateRange(String startDate, String endDate) throws ServiceException {

        String commandSql = "SELECT * FROM Artwork WHERE creationDate BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildArtwork(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artwork createArtwork(Artwork artwork) throws ServiceException {
        String commandSql = "INSERT INTO Artwork (nameArtwork, price, dimensionCm, dimensionIN, mediumArtwork, " +
                "creationDate, category, collectingInstitution, slugReferenceArtist," +
                " referenceImage, idArtist, idGallery) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, artwork.getName());
            preparedStatement.setDouble(2, artwork.getPrice());
            preparedStatement.setString(3, artwork.getDimensionCm() );
            preparedStatement.setString(4, artwork.getDimensionIN());
            preparedStatement.setString(5, artwork.getMedium());
            preparedStatement.setString(6, artwork.getCreationDate());
            preparedStatement.setString(7, artwork.getCategory());

            //-------------------- The following 2 values are set automatically, using the artists id and gallery's id --------------------//
            // get the collectingInstitute with gallery's id:
            preparedStatement.setString(8, getGalleryByID(artwork.getIdGallery()).getNameGallery());
            // get the slugReferenceArtist;
            String slugReferenceArtist = ArtworkUtils.mergeArtistAndArtwork(getArtistByID(artwork.getIdArtist()).getName(), artwork.getName());
            preparedStatement.setString(9, slugReferenceArtist);
            //---------------------------------------------------------------------------------------------------------------------------------
            preparedStatement.setString(10, artwork.getReferenceImage());
            preparedStatement.setInt(11, artwork.getIdArtist());
            preparedStatement.setInt(12, artwork.getIdGallery());

            int affectedRows = preparedStatement.executeUpdate();
            // check with teachers for a more suitable solution;
            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    artwork.setId(generatedKeys.getInt(1));

                }
            }
            return getArtworkByID(artwork.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artwork updateArtwork(Artwork artwork) throws ServiceException {
        String commandSql = "UPDATE Artwork\n" +
                "SET nameArtwork = ?, price = ?, dimensionCm = ?, dimensionIN = ?, mediumArtwork = ?, creationDate = ?, " +
                "category = ?, collectingInstitution = ?, slugReferenceArtist = ?, referenceImage = ?, idArtist = ?, idGallery = ? " +
                "WHERE idArtwork = " + artwork.getId();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, artwork.getName());
            preparedStatement.setDouble(2, artwork.getPrice());
            preparedStatement.setString(3, artwork.getDimensionCm());
            preparedStatement.setString(4, artwork.getDimensionIN());
            preparedStatement.setString(5, artwork.getMedium());
            preparedStatement.setString(6, artwork.getCreationDate());
            preparedStatement.setString(7, artwork.getCategory());

            //-------------------- The following 2 values are set automatically, using the artists id and gallery's id --------------------//
            // get the collectingInstitute with gallery's id:
            preparedStatement.setString(8, getGalleryByID(artwork.getIdGallery()).getNameGallery());
            // get the slugReferenceArtist;
            String slugReferenceArtist = ArtworkUtils.mergeArtistAndArtwork(getArtistByID(artwork.getIdArtist()).getName(), artwork.getName());
            preparedStatement.setString(9, slugReferenceArtist);
            //---------------------------------------------------------------------------------------------------------------------------------

            preparedStatement.setString(10, artwork.getReferenceImage());
            preparedStatement.setInt(11, artwork.getIdArtist());
            preparedStatement.setInt(12, artwork.getIdGallery());

            int affectedRows = preparedStatement.executeUpdate();
            return getArtworkByID(artwork.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artwork deleteArtwork(int id) throws ServiceException {

        String commandSql = "DELETE FROM Artwork WHERE idArtwork = " + id;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                Artwork deletedArtwork = new Artwork();
                deletedArtwork.setId(id);
                return deletedArtwork;
            }
            else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private List<Artwork> buildArtwork (ResultSet resultSet) throws SQLException {
        List<Artwork> artworks = new ArrayList<>();
        while (resultSet.next()) {
            Artwork artwork = new Artwork();
            artwork.setId(resultSet.getInt("idArtwork"));
            artwork.setPrice(resultSet.getDouble("price"));
            artwork.setDimensionCm(resultSet.getString("dimensionCm"));
            artwork.setDimensionIN(resultSet.getString("dimensionIN"));
            artwork.setName(resultSet.getString("nameArtwork"));
            artwork.setMedium(resultSet.getString("mediumArtwork"));
            artwork.setCreationDate(resultSet.getString("creationDate"));
            artwork.setCategory(resultSet.getString("category"));
            artwork.setCollectingInstitution(resultSet.getString("collectingInstitution"));
            artwork.setSlugReferenceArtist(resultSet.getString("slugReferenceArtist"));
            artwork.setReferenceImage(resultSet.getString("referenceImage"));
            artwork.setIdArtist(resultSet.getInt("idArtist"));
            artwork.setIdGallery(resultSet.getInt("idGallery"));
            artworks.add(artwork);
        }

        return artworks;
    }


    //Gallery methods ------------------------------------------------------------------------------------------------
    @Override
    public List<Gallery> getAllGalleries() throws ServiceException {

        String commandSQL = "SELECT * FROM Gallery";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

             return buildGalleries(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Gallery getGalleryByID(int id) throws ServiceException {
        Gallery gallery;
        String commandSQL = "SELECT * FROM Gallery WHERE idGallery = " + id;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            List<Gallery> galleries = buildGalleries(resultSet);
            if (!galleries.isEmpty()){
                gallery = galleries.get(0);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return gallery;
    }

    @Override
    public List<Gallery> getGalleryByName(String name) throws ServiceException {

        String commandSql = "SELECT * FROM Gallery WHERE nameGallery LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, name + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
             return buildGalleries(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Gallery> getGalleryByRegion(String region) throws ServiceException {

        String commandSql = "SELECT * FROM Gallery WHERE regionName = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, region );

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
              return buildGalleries(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Gallery createGallery(Gallery gallery) throws ServiceException {
        String commandSql = "INSERT INTO Gallery (nameGallery, email, regionName, referenceImage) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, gallery.getNameGallery());
            preparedStatement.setString(2, gallery.getEmail());
            preparedStatement.setString(3, gallery.getRegionName());
            preparedStatement.setString(4, gallery.getReferenceImage());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    gallery.setId(generatedKeys.getInt(1));
                }
            }

            return gallery;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Gallery updateGallery(Gallery gallery) throws ServiceException {
        String commandSql = "UPDATE Gallery SET nameGallery = ?, email = ?, regionName = ?, referenceImage = ?" +
                "WHERE idGallery = " + gallery.getId();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, gallery.getNameGallery());
            preparedStatement.setString(2, gallery.getEmail());
            preparedStatement.setString(3, gallery.getRegionName());
            preparedStatement.setString(4, gallery.getReferenceImage());

            int affectedRows = preparedStatement.executeUpdate();

            return gallery;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Gallery deleteGallery(int id) throws ServiceException {
        String commandSql = "DELETE FROM Gallery WHERE idGallery = " + id;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                Gallery deletedGallery = new Gallery();
                deletedGallery.setId(id);
                return deletedGallery;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // to be used when refactoring :
    private List<Gallery> buildGalleries(ResultSet resultSet) throws SQLException {
        List<Gallery> galleries = new ArrayList<>();
        while (resultSet.next()) {
            Gallery gallery = new Gallery();
            gallery.setId(resultSet.getInt("idGallery"));
            gallery.setNameGallery(resultSet.getString("nameGallery"));
            gallery.setEmail(resultSet.getString("email"));
            gallery.setRegionName(resultSet.getString("regionName"));
            gallery.setReferenceImage(resultSet.getString("referenceImage"));

            // add a single gallery to the list of galleries
            galleries.add(gallery);
        }
        return galleries;
    }

    // Exhibition methods-----------------------------------------------------------------------------------------------
    @Override
    public List<Exhibition> getAllExhibitions() throws ServiceException {

        String commandSQL = "SELECT * FROM Exhibition";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

             return buildExhibitions(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Exhibition getExhibitionByID(int id) throws ServiceException {
        Exhibition exhibition;
        String commandSQL = "SELECT * FROM Exhibition WHERE idExhibition = " + id;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            List<Exhibition> exhibitions = buildExhibitions(resultSet);
            if (!exhibitions.isEmpty()){
                exhibition = exhibitions.get(0);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibition;
    }

    @Override
    public List<Exhibition> getExhibitionByName(String name) throws ServiceException {

        String commandSql = "SELECT * FROM Exhibition WHERE nameExhibition LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildExhibitions(resultSet);
            }

        } catch (SQLException e) {
            throw new ServiceException("Error retrieving exhibitions by name", e);
        }

    }

    @Override
    public List<Exhibition> getExhibitionByStatus(String status) throws ServiceException {
        String commandSql = "SELECT * FROM Exhibition WHERE Exstatus = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, status);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildExhibitions(resultSet);
            }

        } catch (SQLException e) {
            throw new ServiceException("Error retrieving exhibitions by Status", e);
        }

    }

    @Override
    public List<Exhibition> getExhibitionByStartDate(String startDate) throws ServiceException {

        String commandSql = "SELECT * FROM Exhibition WHERE startDate = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, startDate);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildExhibitions(resultSet);
            }

        } catch (SQLException e) {
            throw new ServiceException("Error retrieving exhibitions by Start date", e);
        }

    }

    @Override
    public List<Exhibition> getExhibitionByEndDate(String endDate) throws ServiceException {
        String commandSql = "SELECT * FROM Exhibition WHERE endDate = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, endDate);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return buildExhibitions(resultSet);
            }

        } catch (SQLException e) {
            throw new ServiceException("Error retrieving exhibitions by End date", e);
        }
    }

    @Override
    public List<Exhibition> getExhibitionsByGallery(int galleryId) throws ServiceException {

        String commandSql = "SELECT * FROM Exhibition WHERE idGallery = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setInt(1, galleryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
               return buildExhibitions(resultSet);
            }

        } catch (SQLException e) {
            throw new ServiceException("Error retrieving exhibitions by name", e);
        }

    }

    @Override
    public Exhibition createExhibition(Exhibition exhibition) throws ServiceException {
        String commandSql = "INSERT INTO Exhibition (nameExhibition, startDate, endDate, Exdescription, Exstatus, idGallery, referenceImage) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, exhibition.getNameExhibition());
            // check if the dates are okay
            if (ExhibitionUtils.isEndDateBeforeStartDate(exhibition.getStartDate(), exhibition.getEndDate())) {
                throw new ServiceException("End date must be after start date");
            }
            preparedStatement.setDate(2, Date.valueOf(exhibition.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(exhibition.getEndDate()));
            preparedStatement.setString(4, exhibition.getExdescription());

            // If dates are okay, then we need to check if the exhibition is opened or closed according to the endDate
            if(ExhibitionUtils.isEndDateBeforeToday(exhibition.getEndDate())){
                preparedStatement.setString(5, "closed");
            }else{
                preparedStatement.setString(5, "open");
            }
            //---------------------------------------------------------------------------------------------------------
            preparedStatement.setInt(6, exhibition.getIdGallery());
            preparedStatement.setString(7, exhibition.getReferenceImage());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    exhibition.setId(generatedKeys.getInt(1));
                }
            }

            return getExhibitionByID(exhibition.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Exhibition updateExhibition(Exhibition exhibition) throws ServiceException {
        String commandSql = "UPDATE Exhibition SET nameExhibition = ?, startDate = ?, endDate = ?, Exdescription = ?, " +
                "Exstatus = ?, idGallery = ?, referenceImage = ? WHERE idExhibition = " + exhibition.getId();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, exhibition.getNameExhibition());

            // Verify that the dates are correct
            if (ExhibitionUtils.isEndDateBeforeStartDate(exhibition.getStartDate(), exhibition.getEndDate())) {
                throw new ServiceException("End date must be after start date");
            }
            preparedStatement.setDate(2, Date.valueOf(exhibition.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(exhibition.getEndDate()));
            preparedStatement.setString(4, exhibition.getExdescription());

            // If dates are okay, then we need to check if the exhibition is opened or closed according to the endDate
            if(ExhibitionUtils.isEndDateBeforeToday(exhibition.getEndDate())){
                preparedStatement.setString(5, "closed");
            }else{
                preparedStatement.setString(5, "open");
            }
            //--------------------------------------------------------------------------------------------------------
            preparedStatement.setInt(6, exhibition.getIdGallery());
            preparedStatement.setString(7, exhibition.getReferenceImage());

            int affectedRows = preparedStatement.executeUpdate();
            return exhibition;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Exhibition deleteExhibition(int id) throws ServiceException {
        String commandSql = "DELETE FROM Exhibition WHERE idExhibition = " + id;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                Exhibition deletedExhibition = new Exhibition();
                deletedExhibition.setId(id);
                return deletedExhibition;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Exhibition> buildExhibitions(ResultSet resultSet) throws SQLException {

        List<Exhibition> exhibitions = new ArrayList<>();
        while (resultSet.next()) {
            Exhibition exhibition = new Exhibition();

            exhibition.setId(resultSet.getInt("idExhibition"));
            exhibition.setNameExhibition(resultSet.getString("nameExhibition"));
            exhibition.setStartDate(resultSet.getDate("startDate").toLocalDate());
            exhibition.setEndDate(resultSet.getDate("endDate").toLocalDate());
            exhibition.setExdescription(resultSet.getString("Exdescription"));
            exhibition.setExstatus(resultSet.getString("Exstatus"));
            exhibition.setIdGallery(resultSet.getInt("idGallery"));
            exhibition.setReferenceImage(resultSet.getString("referenceImage"));

            // add a single exhibition to the list of exhibitions
            exhibitions.add(exhibition);
        }

        return exhibitions;
    }

    @Override
    public List<Nationality> getNationalities() throws ServiceException {

        List<Nationality> nationalities = new ArrayList<>();
        String commandSQL = "SELECT * FROM Nationality";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {
                Nationality nationality = new Nationality();
                nationality.setNationality(resultSet.getString("nationalityName"));

                nationalities.add(nationality);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nationalities;
    }

    @Override
    public List<Region> getRegions() throws ServiceException {

        List<Region> regions = new ArrayList<>();

        String commandSQL = "SELECT * FROM region";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {
                Region region = new Region();
                region.setRegion(resultSet.getString("regionName"));
                regions.add(region);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return regions;
    }

}
