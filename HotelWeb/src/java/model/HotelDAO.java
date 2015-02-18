package model;

import entity.Hotel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cvadmin
 */

public class HotelDAO implements IHotelDAO {
    private DB_Accessor db;
    
    public static final String driverClass = "com.mysql.jdbc.Driver";
    public static final String url = "jdbc:mysql://127.0.0.1:3306/hotel";
    public static final String userName = "admin";
    public static final String password = "admin";
    
    public HotelDAO () {
	db = new DB_MySql();
    }
    
    @Override
    public List<Hotel> getAllHotels() throws ClassNotFoundException, SQLException {
	List<Hotel> hotels = new ArrayList<>();
	
	db.openConnection(driverClass, url, userName, password);
	List<Map<String,Object>> rawData = db.getAllRecords ("hotel");
	
	for ( Map<String,Object> record : rawData ) {
	    Hotel hotel = new Hotel();
	    hotel.setHotelId(Integer.parseInt(record.get("hotel_id").toString()));
	    hotel.setHotelName(record.get("hotel_name").toString());
	    hotel.setStreetAddress(record.get("street_address").toString());
	    hotel.setCity(record.get("city").toString());
	    hotel.setState(record.get("state").toString());
	    hotel.setPostalCode(record.get("postal_code").toString());
	    hotel.setNotes(record.get("notes").toString());
	    hotels.add(hotel);
	}
	return hotels;
    }

    @Override
    public Hotel getHotelByName (String hotelName) throws ClassNotFoundException, SQLException {
	if (hotelName == null || hotelName.isEmpty()) {
	    throw new IllegalArgumentException("Hotel Name Not found");
	}
	
	db.openConnection(driverClass, url, userName, password);
	Map<String, Object> record = db.getRecordById("hotel", "hotel_name", hotelName);
	Hotel hotel = new Hotel();
	hotel.setHotelId(Integer.parseInt(record.get("hotel_id").toString()));
	hotel.setHotelName(record.get("hotel_name").toString());
	hotel.setStreetAddress(record.get("street_address").toString());
	hotel.setCity(record.get("city").toString());
	hotel.setState(record.get("state").toString());
	hotel.setPostalCode(record.get("postal_code").toString());
	hotel.setNotes(record.get("notes").toString());

	return hotel;
    }
    
    @Override
    public Hotel getHotelById (long hotelId) throws ClassNotFoundException, SQLException {
	if (hotelId == 0) {
	    throw new IllegalArgumentException("Hotel ID Not provided");
	}
	
	db.openConnection(driverClass, url, userName, password);
	Map<String, Object> record = db.getRecordById("hotel", "hotel_id", new Long(hotelId));
	Hotel hotel = new Hotel();
	hotel.setHotelId(Integer.parseInt(record.get("hotel_id").toString()));
	hotel.setHotelName(record.get("hotel_name").toString());
	hotel.setStreetAddress(record.get("street_address").toString());
	hotel.setCity(record.get("city").toString());
	hotel.setState(record.get("state").toString());
	hotel.setPostalCode(record.get("postal_code").toString());
	hotel.setNotes(record.get("notes").toString());

	return hotel;
    }
    
    @Override
    public long updateHotel(Hotel hotel) throws ClassNotFoundException, SQLException {
	Map<String, Object> updKeyRec = new HashMap<>();
	updKeyRec.put("hotel_id", hotel.getHotelId());
	
	Map<String, Object> updValRec = new HashMap<>();
	updValRec.put("hotel_name", hotel.getHotelName());
	updValRec.put("street_address", hotel.getStreetAddress());
	updValRec.put("city", hotel.getCity());
	updValRec.put("state", hotel.getState());
	updValRec.put("postal_code", hotel.getPostalCode());
	updValRec.put("notes", hotel.getNotes());

	db.openConnection(driverClass, url, userName, password);
	long retVal = db.updateRecords("hotel", updKeyRec, updValRec);
	
	return retVal;
    }

    @Override
    public long deleteHotel(Hotel hotel) throws ClassNotFoundException, SQLException {
	Map<String, Object> delKeyRec = new HashMap<>();
	delKeyRec.put("hotel_id", hotel.getHotelId());

	db.openConnection(driverClass, url, userName, password);
	long retVal = db.deleteRecords("hotel", delKeyRec);
	
	return retVal;
    }

    @Override
    public long insertHotel(Hotel hotel) throws ClassNotFoundException, SQLException {
	Map<String, Object> insRec = new HashMap<>();
	insRec.put("hotel_id", 0);
	insRec.put("hotel_name", hotel.getHotelName());
	insRec.put("street_address", hotel.getStreetAddress());
	insRec.put("city", hotel.getCity());
	insRec.put("state", hotel.getState());
	insRec.put("postal_code", hotel.getPostalCode());
	insRec.put("notes", hotel.getNotes());

	db.openConnection(driverClass, url, userName, password);
	long retVal = db.insertRecord("hotel", insRec);
	
	return retVal;
    }
    
    public static void main(String[] args) throws Exception {
	IHotelDAO hotelDao = new HotelDAO();
        Hotel hotel = new Hotel();
        
        hotel.setHotelName("Happy Hotel");
        hotel.setStreetAddress("1234 Main street");
        hotel.setPostalCode("53311");
        hotel.setCity("Milwaukee");
        hotelDao.updateHotel(hotel);
        
//	
//	List<Hotel> hotels = hotelDao.getAllHotels();
//	for ( Hotel hotel: hotels) {
//	    System.out.println(hotel);
//        
//	}
//    
    }
}


