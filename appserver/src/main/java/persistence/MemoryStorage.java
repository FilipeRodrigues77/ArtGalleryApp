package persistence;



import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Gallery;
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
public class MemoryStorage implements ArtistService, ArtworkService, GalleryService, ExhibitionService {

    private final String DB_URL  = "jdbc:mysql://localhost:3306/IAProj";
    private final String DB_USER = "nuel", DB_PASSWORD = "testing12345";


    // Artist methods
    @Override
    public List<Artist> getAllArtists() throws ServiceException {

        List<Artist> artists = new ArrayList<>();
        String commandSQL = "SELECT * FROM Artist";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {
                Artist artist = new Artist();

                artist.setId(resultSet.getInt("idArtist"));
                artist.setName(resultSet.getString("nameArtist"));
                artist.setBirthdate(resultSet.getString("birthDate"));
                artist.setDeathdate(resultSet.getString("deathDate"));
                artist.setBiography(resultSet.getString("biography"));
                artist.setNationality(resultSet.getString("nationalityName"));
                artist.setSlug(resultSet.getString("slug"));
                artist.setSlug(resultSet.getString("slug"));

                // add a single artist to the list of artists
                artists.add(artist);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artists;

    }

    @Override
    public Artist getArtistByID(int id) throws ServiceException {

        Artist artist = null;
        String commandSQL = "SELECT * FROM Artist WHERE idArtist = " + id;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {

                artist = new Artist();
                artist.setId(resultSet.getInt("idArtist")); // THIS IS OUR DB ID, NOT ARTY´S
                artist.setName(resultSet.getString("nameArtist"));
                artist.setBirthdate(resultSet.getString("birthDate"));
                artist.setDeathdate(resultSet.getString("deathDate"));
                artist.setBiography(resultSet.getString("biography"));
                artist.setNationality(resultSet.getString("nationalityName"));
                artist.setSlug(resultSet.getString("slug"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artist;
    }

    @Override
    public List<Artist> getArtistByName(String name) throws ServiceException {

        List<Artist> artists = new ArrayList<>();
        String commandSql = "SELECT * FROM Artist WHERE nameArtist LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, name + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Artist artist = new Artist();
                    artist.setId(resultSet.getInt("idArtist"));
                    artist.setName(resultSet.getString("nameArtist"));
                    artist.setBirthdate(resultSet.getString("birthDate"));
                    artist.setDeathdate(resultSet.getString("deathDate"));
                    artist.setBiography(resultSet.getString("biography"));
                    artist.setNationality(resultSet.getString("nationalityName"));
                    artist.setSlug(resultSet.getString("slug"));

                    artists.add(artist);
                }
            }

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }

        return artists;
    }

    @Override
    public Artist createArtist(Artist artist) throws ServiceException {

        String commandSql = "INSERT INTO Artist (nameArtist, birthDate, deathDate, biography, " +
                "nationalityName, slug) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, artist.getName());
            preparedStatement.setString(2, artist.getBirthdate());
            preparedStatement.setString(3, artist.getDeathdate());
            preparedStatement.setString(4, artist.getBiography());
            preparedStatement.setString(5, artist.getNationality());
            preparedStatement.setString(6, artist.getSlug());

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
                "SET nameArtist = ?, birthDate = ?, deathDate = ?, biography = ?, nationalityName = ?, slug = ?\n" +
                "WHERE idArtist = " + artist.getId();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, artist.getName());
            preparedStatement.setString(2, artist.getBirthdate());
            preparedStatement.setString(3, artist.getDeathdate());
            preparedStatement.setString(4, artist.getBiography());
            preparedStatement.setString(5, artist.getNationality());
            preparedStatement.setString(6, artist.getSlug());

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


    // Artwork methods
    @Override
    public List<Artwork> getAllArtworks() throws ServiceException {
        List<Artwork> artworks = new ArrayList<>();
        String commandSQL = "SELECT * FROM Artwork";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

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

                // add a single artwork to the list of artworks
                artworks.add(artwork);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artworks;

    }

    @Override
    public Artwork getArtworkByID(int id) throws ServiceException {

        Artwork artwork = null;
        String commandSQL = "SELECT * FROM Artwork WHERE idArtwork = " + id;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {

                artwork = new Artwork();
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


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artwork;
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

    //Gallery methods
    @Override
    public List<Gallery> getAllGalleries() throws ServiceException {
        List<Gallery> galleries = new ArrayList<>();
        String commandSQL = "SELECT * FROM Gallery";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {
                Gallery gallery = new Gallery();

                gallery.setId(resultSet.getInt("idGallery"));
                gallery.setNameGallery(resultSet.getString("nameGallery"));
                gallery.setEmail(resultSet.getString("email"));
                gallery.setRegionName(resultSet.getString("regionName"));

                // add a single gallery to the list of galleries
                galleries.add(gallery);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return galleries;
    }

    @Override
    public Gallery getGalleryByID(int id) throws ServiceException {
        Gallery gallery = null;
        String commandSQL = "SELECT * FROM Gallery WHERE idGallery = " + id;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {
                gallery = new Gallery();
                gallery.setId(resultSet.getInt("idGallery"));
                gallery.setNameGallery(resultSet.getString("nameGallery"));
                gallery.setEmail(resultSet.getString("email"));
                gallery.setRegionName(resultSet.getString("regionName"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gallery;
    }

    @Override
    public Gallery createGallery(Gallery gallery) throws ServiceException {
        String commandSql = "INSERT INTO Gallery (nameGallery, email, regionName) " +
                "VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, gallery.getNameGallery());
            preparedStatement.setString(2, gallery.getEmail());
            preparedStatement.setString(3, gallery.getRegionName());

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
        String commandSql = "UPDATE Gallery SET nameGallery = ?, email = ?, regionName = ?" +
                "WHERE idGallery = " + gallery.getId();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(commandSql)) {

            preparedStatement.setString(1, gallery.getNameGallery());
            preparedStatement.setString(2, gallery.getEmail());
            preparedStatement.setString(3, gallery.getRegionName());

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


    // Exhibition methods
    @Override
    public List<Exhibition> getAllExhibitions() throws ServiceException {
        List<Exhibition> exhibitions = new ArrayList<>();
        String commandSQL = "SELECT * FROM Exhibition";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {
                Exhibition exhibition = new Exhibition();

                exhibition.setId(resultSet.getInt("idExhibition"));
                exhibition.setNameExhibition(resultSet.getString("nameExhibition"));
                exhibition.setStartDate(resultSet.getDate("startDate").toLocalDate());
                exhibition.setEndDate(resultSet.getDate("endDate").toLocalDate());
                exhibition.setExdescription(resultSet.getString("Exdescription"));
                exhibition.setExstatus(resultSet.getString("Exstatus"));
                exhibition.setIdGallery(resultSet.getString("idGallery"));

                // add a single exhibition to the list of exhibitions
                exhibitions.add(exhibition);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitions;
    }

    @Override
    public Exhibition getExhibitionByID(int id) throws ServiceException {
        Exhibition exhibition = null;
        String commandSQL = "SELECT * FROM Exhibition WHERE idExhibition = " + id;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(commandSQL)) {

            while (resultSet.next()) {
                exhibition = new Exhibition();
                exhibition.setId(resultSet.getInt("idExhibition"));
                exhibition.setNameExhibition(resultSet.getString("nameExhibition"));
                exhibition.setStartDate(resultSet.getDate("startDate").toLocalDate());
                exhibition.setEndDate(resultSet.getDate("endDate").toLocalDate());
                exhibition.setExdescription(resultSet.getString("Exdescription"));
                exhibition.setExstatus(resultSet.getString("Exstatus"));
                exhibition.setIdGallery(resultSet.getString("idGallery"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibition;
    }

    @Override
    public Exhibition createExhibition(Exhibition exhibition) throws ServiceException {
        String commandSql = "INSERT INTO Exhibition (nameExhibition, startDate, endDate, Exdescription, Exstatus, idGallery ) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

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
            preparedStatement.setString(6, exhibition.getIdGallery());

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
                "Exstatus = ?, idGallery = ? WHERE idExhibition = " + exhibition.getId();

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
            preparedStatement.setString(6, exhibition.getIdGallery());

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

}
