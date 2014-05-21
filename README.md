jDao-boilerplate
================

best start template for dynamic web project based on struts&amp;spring written in java.


> This project is based on Spring MVC & Spring4 & Hibernate3. 
> Package Dependency's management is totally handled to maven, which means *.jar files need not
 to be uploaded to the version control system: subversion, git-scm, etc.


## Modules
>There area basically three modules integrated into erp_demo project. 

 
### Data Access Module
Class GenericDao.java is the interface of GenericDaoImpl.java. The names of the two classes tell us the story we need to be aware of -- They are based on Generic Programming. 

The Interface GenericDao.java contains some lines of interpretation, which tell the usage of each method and some occasional attentions for programmer. Please follow these rules, otherwise you might encounter something you are not expected to.

Both the interface and the implementation are not important for developers who want to make use of this framework. If you want to write a DAO class for your own pojo(Plain Old Java Object), here is the deal:

>Area.java  --- pojo

```java
public class Area extends EntityObject{
    
	@Id
	private String id;
	private String areaName;
	private int areaRemoteId;
	private double longitude;
	private double latitude;
	private double radius;
	
	@OneToMany(mappedBy="area", fetch=FetchType.LAZY)
	private List<Building> buildings;
    
    /*** escape all the get and set methods, also the construtors ***/
}
```

> AreaDao.java  --- DAO 

```java
@Repository("areaDao")
public class AreaDao extends GenericDaoImpl<Area>{
	@Override
	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}

```

Yes, that's all. Then, you can enjoy the convenience which Generic Programming brings to you.

```java
List<Tuple> aeras = areaDao.findProjectedByParamsInOrder(CriteriaWrapper.instance().and(Restrictions.gt("radius", 500.2)), ProjectionWrapper.instance("id","areaName"), Sortable.instance("radius", SortOrder.ASCEND));

//only need to remember: CriteriaWrapper for update and delete must be handled with difference. ' ' is needed to wrap the value of the given field.  
areaDao.updateMultiByParams(CriteriaWrapper.instance().and(Restrictions.eq("areaName", "'本部校区'"),Restrictions.ge("radius", 500.2)), UpdateWrapper.instance().inc("radius", 1000.2));


```


### EntityObject
This class should be the super class for your entity class. You will not want to miss all the extra bonus I provide for you.

>Area.java
```
public class Area extends EntityObject{ ... }
```

```
Area area = new Area();
		
area.setAreaName("爱恩校区");
area.setId(UUIDGenerator.randomUUID());
area.setAreaRemoteId(2313);
area.setLatitude(231.1);
area.setLongitude(221.21);
area.setRadius(111);
		
//you will obtain a super-good json object, which can to cast to String easily.
JSONObject areaJson = area.toJSON();
System.out.println(areaJson.toString());
```

### POJO & DTO
Entity Class for Hibernate is not always appropriate to send to front-end. Sometimes you will need a DTO class which is correspondent to your POJO class.

>Building.java --- POJO


```java
public class Building extends EntityObject{
	
	@Id
	private String id;
	private String buildingName;
    
    //I hate the mapping config, I only want my areaId(String), not the whole Area Object. However, I still depend on it a lot. I cannot change the annotation.
	@ManyToOne
	@JoinColumn(name="areaId")
	private Area area;
	private int buildingRemoteId;
	private double longitude;
	private double latitude;
	private double radius;
    
    /*** escape all the get and set methods, also the constructors ***/
}
```

>BuildingDto.java --- DTO


```java
public class BuildingDto extends EntityObject{

	
	private String id;
	private String buildingName;
    
    //Yeah, that's good.I only want the String.
	private String areaId;
    
	private int buildingRemoteId;
	private double longitude;
	private double latitude;
	private double radius;
	/*** escape all the get and set methods, also the constructors ***/
}
```

The instanct above is simple, but sometimes the number of fields you want to tweak is abundent. Still, there might to a lot same fields between you POJO class and your DTO class. Give each field a set and get? That is way too awful. While I provide something for you:

```java
Area area = getArea();

AreaDto areaDto = (AreaDto)Util.pojoToDto(area);
//now the DTO has all the same-name fields being set. Save your time to get and set on each field with the same name.
//The opposite works too.
Area area = (Area)Util.dtoToPojo(areaDto);
```
>Only the Prerequsite is your config file. You should tell the framework which package you pojo class located and which package your dto class located.

```
package.pojo=com.erp.pojos
package.dto=com.erp.pojos.dto
```
>The Name of your DTO class should follow the rule below:
`pojo_class_name+Dto.java`
>
>Area.java ---> AreaDto.java

Enjoy :)