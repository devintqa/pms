<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<%@include file="Script.jsp"%>
<script src="<c:url value="/resources/js/handsontable.full.js" />"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/handsontable.full.min.css" />">
<script>
	document
			.addEventListener(
					"DOMContentLoaded",
					function() {

						function getNewData() {
							return [["1","5980.00","Earth Work Excavation"," 108.00 "," 645,840.00 "," 3,399.36 ",""," 367,130.88 "," - "," 2,580.64 "," - "," 278,709.12 ","","","",""],["2","275.00","Earth Work Excavation"," 55.00 "," 15,125.00 "," 270.46 ",""," 14,875.30 "," - "," 4.54 "," - "," 249.70 ","","","",""],["3","1794.00","Refilling Excavated Earth"," 20.00 "," 35,880.00 "," 1,797.42 ",""," 35,948.40 "," 3.42 "," - "," 68.40 "," - ","","","",""],["4","2605.00","Gravel Filling"," 325.00 "," 846,625.00 "," - ",""," - "," - "," 2,605.00 "," - "," 846,625.00 ","","","",""],["5","2365.00","Filling Sand"," 575.00 "," 1,359,875.00 "," 990.66 ",""," 569,629.50 "," - "," 1,374.34 "," - "," 790,245.50 ","","","",""],["6","2980.00","Quarry Dust Filling"," 260.00 "," 774,800.00 "," 2,790.29 ",""," 725,475.40 "," - "," 189.71 "," - "," 49,324.60 ","","","",""],["7","1280.00","PCC 1:5:10"," 2,700.00 "," 3,456,000.00 "," 614.34 ",""," 1,658,718.00 "," - "," 665.66 "," - "," 1,797,282.00 ","","","",""],["8","5.00","Installation charges inclusive of Transportation to site and the bored pile equipment employed for the works including setting up of pile driving equipment with all accessories and tools at each of the pile location based on the sequence of opearation approved by the departmental Engineers irrespective of the location and distance involved in movement of pile driving equipment etc., complete as directed by the departmental officers."," 105,000.00 "," 525,000.00 "," 5.00 ",""," 525,000.00 "," - "," - "," - "," - ","","","",""],["9","","Initial Load Test as per ISI 2911 Part IV 1985 including cost of single pile (250% pile capacity) including reinforcement etc.",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","2.00","450mm dia "," 155,000.00 "," 310,000.00 "," 2.00 ",""," 310,000.00 "," - "," - "," - "," - ","","","",""],["b","2.00","600mm dia "," 155,000.00 "," 310,000.00 "," 2.00 ",""," 310,000.00 "," - "," - "," - "," - ","","","",""],["10","","Routine Load Test as per IS 2911 Part IV 1995(150% of pile capacity) including labour charges and all other incidental charges including hire charges for jacks, measuring devices, structural frame work for platform, kent ledges, etc. as required.",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","3.00","450mm dia "," 105,000.00 "," 315,000.00 "," 3.00 ",""," 315,000.00 "," - "," - "," - "," - ","","","",""],["b","5.00","600mm dia "," 105,000.00 "," 525,000.00 "," 5.00 ",""," 525,000.00 "," - "," - "," - "," - ","","","",""],["11","","Installation of vertical Bored cast in situ piles in all types of soils, using Ready Mix Concrete of M30 grade, with sulphate resistant cement, fine aggregate, 20mm and down graded HBGS jelly coarse aggregate. The placing of reinforcement cage in bore hole in correct position, cost of boring to the required depth with Bentonite solution with temproary casing of required length, conveyance of RMC to site and including all tools and plants, shifting the plants and equipments from one point to another, excavation of mud pits for circulation of Bentonite solution, closing the mud pits to original level and mud removal with initial lead and lift but including dismantling the concrete above the cut off level etc. complete as directed by the departmental officers including socketing. The reinforcement cage should be welded properly for lifting. The concrete cubes to be tested as per IS 456-2000. Acceptance criteria by clause 16.1 . Frequency of testing should be as per clause 15.2.2. The length of pile for payment shall be measured 10m hip of shoe to the bottom of pile cap The work should be carried out as per IS 2911. Part 1 section 2 Pile depth should be decided by the departmental office at site. \n"," - "," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","1440.00","450mm dia having longitudinal main reinforcement of 8 Nos. of 16mm dia with helical reinforcement at 8mm dia at 200mm pitch"," 3,650.00 "," 5,256,000.00 "," 1,440.00 ",""," 5,256,000.00 "," - "," - "," - "," - ","","","",""],["b","10125.00","600mm dia having longitudinal main reinforcement of 4 Nos. of 20mm dia and 4 Nos of 16mm dia with helical reinforcement at 10mm dia at 200mm pitch"," 5,000.00 "," 50,625,000.00 "," 10,124.95 ",""," 50,624,750.00 "," - "," 0.05 "," - "," 250.00 ","","","",""],["12","4059.00","Basement Centering"," 400.00 "," 1,623,600.00 "," 4,045.16 ",""," 1,618,064.00 "," - "," 13.84 "," - "," 5,536.00 ","","","",""],["13","","Centering",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","39896.00","For plane surfaces such as RCC floor slab, roof slab, beams, lintels, bed blocks, landing slab, waist slab, portico slabs and beams, etc."," 460.00 "," 18,352,160.00 "," 39,779.43 ",""," 18,298,537.80 "," - "," 116.57 "," - "," 53,622.20 ","","","",""],["b","8205.00","Column Centering"," 550.00 "," 4,512,750.00 "," 8,203.32 ",""," 4,511,826.00 "," - "," 1.68 "," - "," 924.00 ","","","",""],["c","2095.00","For plane surfaces such as vertical slab, side slabs of boxing, vertical drops, facia, vertical wall, etc."," 500.00 "," 1,047,500.00 "," 2,035.69 ",""," 1,017,845.00 "," - "," 59.31 "," - "," 29,655.00 ","","","",""],["d","800.00","Circular surfaces"," 1,100.00 "," 880,000.00 "," 799.09 ",""," 878,999.00 "," - "," 0.91 "," - "," 1,001.00 ","","","",""],["e","3420.00","Curved Surfaces "," 675.00 "," 2,308,500.00 "," 3,414.05 ",""," 2,304,483.75 "," - "," 5.95 "," - "," 4,016.25 ","","","",""],["14","5785.00","Providing two legged scaffolding using 15cm casurina Post for toatal height of 4m, the distance between two rows being 1.25m & the spacing of the posts with 0.5m overlap on either side & braces at 2m C/C including longitutinal and traverse middle braces to step up & providing a platform with silveroak planks 40mm thick & 1m width etc., in a complete form using coir & nails etc., as directed by the departmental officers."," 135.00 "," 780,975.00 "," 5,755.15 ",""," 776,945.25 "," - "," 29.85 "," - "," 4,029.75 ","","","",""],["15","106205.00","Additional Structting"," 21.00 "," 2,230,305.00 "," 106,204.18 ",""," 2,230,287.78 "," - "," 0.82 "," - "," 17.22 ","","","",""],["16","","Providing and laying in position READY MIX CONCRETE M30 Grade using ordinary portland cement with water content not exceeding 0.45 including use of plasticizer of approved make & dosage of required grade at all locations and levels using 20mm size & down graded aggregates in all types of works including hire charges for pump and dimping charges to the required locations, machine vibrating, tamping, curing, providing fixtures like fan clamps, beading as specified, charges for conveyance of RMC plant to tthe site or all as required (excluding cost of reinforcement and fabrication charges, centering and shuttering). Mix design details to be got approved before commenecement of the work adopting pour card system by the departmental officers etc., complete as per IS 4926 of 1976/1990.",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","2665.00","Foundation & Basement"," 5,150.00 "," 13,724,750.00 "," 2,565.15 ",""," 13,210,522.50 "," - "," 99.85 "," - "," 514,227.50 ","","","",""],["17","","Providing and laying in position READY MIX CONCRETE M35 Grade using ordinary portland cement with water content not exceeding 0.45 including use of plasticizer of approved make & dosage of required grade at all locations and levels using 20mm size & down graded aggregates in all types of works including hire charges for pump and dimping charges to the required locations, machine vibrating, tamping, curing, providing fixtures like fan clamps, beading as specified, charges for conveyance of RMC plant to tthe site or all as required (excluding cost of reinforcement and fabrication charges, centering and shuttering). Mix design details to be got approved before commenecement of the work adopting pour card system by the departmental officers etc., complete as per IS 4926 of 1976/1990.",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","954.00","In Stilt floor"," 5,200.00 "," 4,960,800.00 "," 878.47 ",""," 4,568,044.00 "," - "," 75.53 "," - "," 392,756.00 ","","","",""],["b","1315.00","In First floor"," 5,250.00 "," 6,903,750.00 "," 1,289.90 ",""," 6,771,975.00 "," - "," 25.10 "," - "," 131,775.00 ","","","",""],["c","822.00","In Second Floor"," 5,300.00 "," 4,356,600.00 "," 805.07 ",""," 4,266,871.00 "," - "," 16.93 "," - "," 89,729.00 ","","","",""],["d","830.00","In Third floor"," 5,350.00 "," 4,440,500.00 "," 806.88 ",""," 4,316,808.00 "," - "," 23.12 "," - "," 123,692.00 ","","","",""],["e","635.00","In Fourth floor"," 5,400.00 "," 3,429,000.00 "," 610.72 ",""," 3,297,888.00 "," - "," 24.28 "," - "," 131,112.00 ","","","",""],["18","","Providing and laying in position READY MIX CONCRETE M35 Grade using ordinary portland cement with water content not exceeding 0.45 including use of plasticizer of approved make & dosage of required grade at all locations and levels using 20mm size & down graded aggregates in all types of works including hire charges for pump and dimping charges to the required locations, machine vibrating, tamping, curing, providing fixtures like fan clamps, beading as specified, charges for conveyance of RMC plant to tthe site or all as required (excluding cost of reinforcement and fabrication charges, centering and shuttering). Mix design details to be got approved before commenecement of the work adopting pour card system by the departmental officers etc., complete as per IS 4926 of 1976/1990.",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","4540.00","PT Beam & Slab"," 5,300.00 "," 24,062,000.00 "," 4,523.45 ",""," 23,974,285.00 "," - "," 16.55 "," - "," 87,715.00 ","","","",""],["19","500.00","Cement Concrete 1:2:4 (One Cement, Two sand and Four hard broken stone jelly) using 20 mm gauge hard broken granite stone jelly for all RCC items of works excluding cost of reinforcement grill and fabricating charges, centering and shuttering but including laying, vibrating with mechanical vibrators, finishing, curing, etc. and providing fixtures like fan clamps in the RCC floor / roof slabs wherever necessary and bearing surfaces of walls, beams etc. shall be finished smooth with Cement Mortar 1:3 (One Cement and Three Sand) and kraft paper laid over it without claiming extra, etc., complete complying with standard specification and as directed by the departmental officers "," 4,700.00 "," 2,350,000.00 "," - ",""," - "," - "," 500.00 "," - "," 2,350,000.00 ","","","",""],["20","285.00","Cement Concrete 1:1.5:3 (One Cement, one and half sand and three hard broken stone jelly) using 20 mm gauge hard broken granite stone jelly for all RCC items of works excluding cost of reinforcement grill and fabricating charges, centering and shuttering but including laying, vibrating with mechanical vibrators, finishing, curing, etc. and providing fixtures like fan clamps in the RCC floor / roof slabs wherever necessary and bearing surfaces of walls, beams etc. shall be finished smooth with Cement Mortar 1:3 (One Cement and Three Sand) and kraft paper laid over it without claiming extra, etc., complete complying with standard specification and as directed by the departmental officers. "," 5,200.00 "," 1,482,000.00 "," - ",""," - "," - "," 285.00 "," - "," 1,482,000.00 ","","","",""],["21","16366.08","Steel"," 6,100.00 "," 99,833,088.00 "," 16,221.01 ",""," 98,948,161.00 "," - "," 145.07 "," - "," 884,927.00 ","","","",""],["22","133.00","PT Steel"," 152,000.00 "," 20,216,000.00 "," 132.95 ",""," 20,208,400.00 "," - "," 0.05 "," - "," 7,600.00 ","","","",""],["23","","Brick work in Cement Mortar 1:6 ( one cement & six sand) using best quality II class ground moulded chamber burnt bricks of size 9\" x 4-3/8\" x 2-3/4\" for foundation and basement including curing, etc., complete complying with standard specifiction.",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["","","In Cement Mortar 1:6 (one cement and six sand)",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","405.50","For foundation & basement"," 4,000.00 "," 1,622,000.00 "," 387.70 ",""," 1,550,800.00 "," - "," 17.80 "," - "," 71,200.00 ","","","",""],["b","587.39","In Ground floor"," 4,000.00 "," 2,349,560.00 "," 410.31 ",""," 1,641,240.00 "," - "," 177.08 "," - "," 708,320.00 ","","","",""],["c","1145.00","In First floor"," 4,050.00 "," 4,637,250.00 "," 533.01 ",""," 2,158,690.50 "," - "," 611.99 "," - "," 2,478,559.50 ","","","",""],["d","1292.00","In Second floor "," 4,100.00 "," 5,297,200.00 "," 1,230.02 ",""," 5,043,082.00 "," - "," 61.98 "," - "," 254,118.00 ","","","",""],["e","1081.00","In Third floor "," 4,150.00 "," 4,486,150.00 "," 1,079.47 ",""," 4,479,800.50 "," - "," 1.53 "," - "," 6,349.50 ","850"," 3,527,500.00 ","",""],["f","377.00","In Fourth floor"," 4,200.00 "," 1,583,400.00 "," - ",""," - "," - "," 377.00 "," - "," 1,583,400.00 ","","","",""],["24","890.00","Weathering course in brick jelly lime concrete using broken brick jelly of size 20mm uniform gauge in pure slaked lime (no sand to be used) to the proportion of brick jelly to lime being 32:12.5 by volume and laid over the RCC roof slab in a single layer of required slope and finished by beating the concrete with wooden beaters of approved pattern, keeping the surface constantly wet by sprinkling lime jaggery water, etc., complete as per standard specification and as directed by the deparmental officers."," 2,560.00 "," 2,278,400.00 "," 826.27 ",""," 2,115,251.20 "," - "," 63.73 "," - "," 163,148.80 ","826"," 3,061,958.32 ","",""],["25","4380.00","Pressed Tiles"," 615.00 "," 2,693,700.00 "," 4,378.47 ",""," 2,692,759.05 "," - "," 1.53 "," - "," 940.95 ","4378"," 2,080,736.51 ","",""],["26","255.00","Brick Jelly Lime Concrete of 1: 2:5 (One Lime,Two Sand and Five Brick jelly ) with 20 mm hard broken brick jelly for Sunken portions including dewatering wherever necessary and laid in layers of not more than 15cm thick, well rammed, consolidated and curing etc. complete complying with standard specification."," 2,000.00 "," 510,000.00 "," 254.97 ",""," 509,940.00 "," - "," 0.03 "," - "," 60.00 ",""," 509,940.00 ","",""],["27","3710.00","Granite Flooring"," 2,310.00 "," 8,570,100.00 "," 3,348.97 "," 2,079.00 "," 6,962,508.63 "," - "," 361.03 "," - "," 1,607,591.37 "," 2,891.97 "," 661,127.48 ","",""],["28","702.00","Concrete Anti Skid Tiles"," 870.00 "," 610,740.00 ","",""," - "," - "," 702.00 "," - "," 610,740.00 ","","","",""],["29","415.00","Wooden Flooring"," 1,575.00 "," 653,625.00 "," - ",""," - "," - "," 415.00 "," - "," 653,625.00 ","","","",""],["30","420.00","f hot applied thermoplastic compound 2.5mm thick including reflactarising glass beads of 250gms per sq.m area. The thickness of 2.5mm is inclusive of surface applied glass beads as per IRC 35. The finished surface has to be leveled uniformly and free from streaks and holes etc., complete as per MORTS specificatrion 803. The average width of the painting will be 12.50cm as per IRC. The rate is inclusive of cost of all materials, labour, taxes, etc., complete complying to the specification and as directed by the departmental officers."," 740.00 "," 310,800.00 "," - ",""," - "," - "," 420.00 "," - "," 310,800.00 ","","","",""],["31","2080.00","Nosing to the granite slas by cutting the granite slabs for 18mm to 20mm thick edges semicircular shape to required size and nosing with semicircular shape and polishing using hand machine and the nosing is made highly smooth and gloosy . the cost includes labour charges and cost of machineries and polishing charges etc.,complete as directed by the departmental officers."," 200.00 "," 416,000.00 "," - ",""," - "," - "," 2,080.00 "," - "," 416,000.00 ","","","",""],["32","7850.00","Vitrified Tiles"," 1,250.00 "," 9,812,500.00 "," 7,707.38 "," 1,125.00 "," 8,670,802.50 "," - "," 142.62 "," - "," 1,141,697.50 "," 7,707.38 "," 1,761,968.73 ","",""],["33","600.00","Ceramic Floor Tiles"," 800.00 "," 480,000.00 "," 598.22 "," 720.00 "," 430,718.40 "," - "," 1.78 "," - "," 49,281.60 "," 598.22 "," 381,358.07 ","",""],["34","3700.00","Dadooing Tile"," 910.00 "," 3,367,000.00 "," 2,385.39 "," 819.00 "," 1,953,634.41 "," - "," 1,314.61 "," - "," 1,413,365.59 "," 2,385.39 "," 1,674,658.28 ","",""],["35","","Acoustic Wall Panelling",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","823.00","Upto 8 feet "," 7,000.00 "," 5,761,000.00 "," - ",""," - "," - "," 823.00 "," - "," 5,761,000.00 ","","","",""],["36","","Acoustic Wall Panelling",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","549.00","Above 8 feet"," 5,700.00 "," 3,129,300.00 "," - ",""," - "," - "," 549.00 "," - "," 3,129,300.00 ","","","",""],["37","1530.00","Granite Cladding"," 2,210.00 "," 3,381,300.00 "," 420.62 "," 1,989.00 "," 836,613.18 "," - "," 1,109.38 "," - "," 2,544,686.82 "," 420.62 "," 200,290.83 ","",""],["38","985.00","SS Door Frame"," 1,290.00 "," 1,270,650.00 "," - ",""," - "," - "," 985.00 "," - "," 1,270,650.00 ","","","",""],["39","","Flush Door Shutter",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","215.00","Single leaf door"," 2,700.00 "," 580,500.00 "," - ",""," - "," - "," 215.00 "," - "," 580,500.00 ","","","",""],["b","275.00","Double leaf door"," 3,200.00 "," 880,000.00 "," - ",""," - "," - "," 275.00 "," - "," 880,000.00 ","","","",""],["40","11745.00","MS Grill"," 80.00 "," 939,600.00 "," - ",""," - "," - "," 11,745.00 "," - "," 939,600.00 ","","","",""],["41","215.00","Aluminium Openable Window"," 3,600.00 "," 774,000.00 "," - ",""," - "," - "," 215.00 "," - "," 774,000.00 ","","","",""],["42","22.00","Aluminium Ventilator"," 4,600.00 "," 101,200.00 "," - ",""," - "," - "," 22.00 "," - "," 101,200.00 ","","","",""],["43","490.00","SS Hand Rail"," 6,500.00 "," 3,185,000.00 "," 489.87 "," 5,850.00 "," 2,865,739.50 "," - "," 0.13 "," - "," 319,260.50 ","489"," 1,445,728.50 ","",""],["44","135.00","SS Hand Rail"," 2,100.00 "," 283,500.00 "," - ",""," - "," - "," 135.00 "," - "," 283,500.00 ","","","",""],["45","560.00","PVC Trims"," 85.00 "," 47,600.00 "," - ",""," - "," - "," 560.00 "," - "," 47,600.00 ","","","",""],["46","10000.00","Armstorng False Ceiling"," 1,280.00 "," 12,800,000.00 "," 8,827.36 "," 1,152.00 "," 10,169,118.72 "," - "," 1,172.64 "," - "," 2,630,881.28 ",""," 4,562,250.00 ","",""],["47","1565.00","Grano Flooring"," 165.00 "," 258,225.00 "," - ",""," - "," - "," 1,565.00 "," - "," 258,225.00 ","","","",""],["48","1810.00","Semi Unitised Structural Glazing System with cost of Glass."," 12,000.00 "," 21,720,000.00 "," 1,748.62 "," 10,800.00 "," 18,885,096.00 "," - "," 61.38 "," - "," 2,834,904.00 ",""," 4,975,501.98 ","",""],["49","46190.00","Plastering with Cement Mortar 1:5 (One cement and Five sand), 12mm thick in all floors including curing etc. complete complying with standard specification and as directed by the departmental officers."," 115.00 "," 5,311,850.00 "," - ",""," - "," - "," 46,190.00 "," - "," 5,311,850.00 ","",""," 26,190.00 "," 3,011,850.00 "],["50","700.00","Plastering with Cement Mortar 1:3 (One Cement and Three Sand) 20mm thick mixed with water proofing compound conforming to BIS at 2% by weight of cement used including finsihing, curing, etc., complete in all floors complying with standard specification and as directed by the departmental officers."," 260.00 "," 182,000.00 "," - ",""," - "," - "," 700.00 "," - "," 182,000.00 ","","","",""],["51","51905.00","Special ceiling plastering and finishing the exposed surface of all RCC items of work such as slabs, beams, sunshades, facia, canopy slab, staircase waist slab, landing slab etc., with Cement Mortar 1:3 (One Cement and Three Sand) 10mm thick including hacking the surfaces, providing cement mortar nosing, beading for sunshades, staircases, steps, landing slabs and curing, etc., in all floors complying with standard specification and as directed by the departmental officers "," 130.00 "," 6,747,650.00 "," - ",""," - "," - "," 51,905.00 "," - "," 6,747,650.00 ","",""," 51,905.00 "," 6,747,650.00 "],["52","34450.00","Emulsion Paint"," 135.00 "," 4,650,750.00 "," - ",""," - "," - "," 34,450.00 "," - "," 4,650,750.00 ","","","",""],["60","","Supplying ,fabricating and erecting in position Tip-up and back push reclined chair in Auditorium with Arm rest to arm rest (c/c) 22? with size of seat cushion: 460mm (+/-5mm) x 475mm (+/-5mm) ), Back cushion: 740mm (+/-5mm) x 495mm (+/-5mm) of polyurethane foam of Density: 50-55 kg/m3 and ) the seat cushion 130mm in the front (+/-5mm) and at the back 105mm (+/-5mm) (tapered type front portion with a hump ) Back cushion bucket type. Headrest 115mm(+/- 5mm) in the centre & 70mm(+/-5mm) at the top, and lumber support contours at the sides 118mm(+/-5mm) etc. All sheet metal parts are powder coated With Arm rest in PU/PPCP with cup holder, ABS molded housing for seat & back cushions .The Frames are of 18 mm thick high pressure steam pressed hard ply wood for seat and the back of 12mm multi bend ply wood. Over the polyurethane foam ,foam lamination of 2 mm is provided and finally finished with fabric of approved colour. Necessary . Seat numbering on inner both the side of the\nchair stands with silicon fluorescent thin stickers has to be provided.. Row number for seat along the aisles. with . Provision for lights on sides along with aisles with the row and the seat numbers display has to beprovided . . Seat and Back cushion housing ABS molded vacuum forming out of 2mm sheet.\n\nSupplying ,fabricating and erecting in position Tip-up and back push reclined chair in Auditorium with Arm rest to arm rest (c/c) 22? with size of seat cushion: 460mm (+/-5mm) x 475mm (+/-5mm) ), Back cushion: 740mm (+/-5mm) x 495mm (+/-5mm) of polyurethane foam of Density: 50-55 kg/m3 and ) the seat cushion 130mm in the front (+/-5mm) and at the back 105mm (+/-5mm) (tapered type front portion with a hump ) Back cushion bucket type. Headrest 115mm(+/- 5mm) in the centre & 70mm(+/-5mm) at the top, and lumber support contours at the sides 118mm(+/-5mm) etc. All sheet metal parts are powder coated With Arm rest in PU/PPCP with cup holder, ABS molded housing for seat & back cushions .The Frames are of 18 mm thick high pressure steam pressed hard ply wood for seat and the back of 12mm multi bend ply wood. Over the polyurethane foam ,foam lamination of 2 mm is provided and finally finished with fabric of approved colour. Necessary . Seat numbering on inner both the side of the\nchair stands with silicon fluorescent thin stickers has to be provided.. Row number for seat along the aisles. with . Provision for lights on sides along with aisles with the row and the seat numbers display has to beprovided . . Seat and Back cushion housing ABS molded vacuum forming out of 2mm sheet.\n\n",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["","","Installation of the chairs: The chairs has to be installed to the existing flooring with\nthe anchor fasteners 12mmx100mm pin type.\n. Spring For tip-up and back push mechanism torsion spring/spring\nsteel IS:4454 1981 grade II\n. Sheet ?metal components HRCA/CRCA Sheet metal IS:1079 1994\na) Side stand 3mm (+/- 0.2mm) thick size: 415mm(+/-\n5mm) x 345mm(+/-5mm) both side bottom circular\ncutting with 140mm radius.\nb) 75mmx25mm 16g 190 mm length tubular pipe for the\nleg wielded to the 3mm plate.\nc) Flat for base of the stands 280mm (+/-2mm) length\n50mm (+/-2mm) x 5mm (+/-0.2mm).\nd) Mechanism components 2mm HRCA\nBack push box 180mm(+/-2mm) x 70(+/-2mm) mm &\nheight of the box 15mm(+/-2mm), ear ?L? bracket\nattached to the box 190mm(+/-2mm) x135mm(+/-\n2mm). With two slot holes for fixing the back.\nTip-up box 180mm (+/-2mm) x 70(+/-2mm) mm &\nheight of the box 15mm (+/-2mm), ear ?L? bracket\nattached to the box 95mm (+/-2mm) x 125mm (+/-\n2mm). With two slot holes to fix the seat.\n\n",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["","","The cost includes cost of materials, labour including supplying ,erecting and fixing in position ,conveyance ,all applicable taxes complete complying to the specifications\n",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["a","1200.00","In Auditorium "," 7,560.00 "," 9,072,000.00 "," 1,060.00 "," 6,804.00 "," 7,212,240.00 "," - "," 140.00 "," - "," 1,859,760.00 ",""," 4,240,000.00 ","",""],["61","1000.00","Chait"," 5,220.00 "," 5,220,000.00 "," 900.00 "," 4,959.00 "," 4,463,100.00 "," - "," 100.00 "," - "," 756,900.00 ",""," 3,500,000.00 ","",""],["62","","Supplying, erecting ,commissioning and testing of Fire Hydrant MS medium 'B' class pipe for wet riser cum down comer of following dia including cost of specials such as elbows, reducers, T fittings etc., complete with necessary flanges asper IS 1239.The pipe should be fixed to the wall by suitable means of specials clamps. The cost includes pipes, specials, clamps welding the joints, grinding the joints,labour chargesetc., complete as directed by the departmental officers.The cost includes Painting the pipe with two coats of Post Office red colour synthetic enamel over a coat of red oxide primer.(pipe make:Jindal).",""," - "," - ",""," - "," - "," - "," - "," - ","","","",""],["78","2.00","Supplying , erecting, testing and commissioning of 100mm 4 way fire brigade inlets including 1 no butterfly valve as directed by the departemntal officers near every Hydrant point etc., complete (make:Newage)."," 23,780.00 "," 47,560.00 "," - ",""," - "," - "," 2.00 "," - "," 47,560.00 ","","","",""],["79","2.00","Supplying, erecting, testing and commissioning MAIN FIRE PUMP (MOTOR DRIVEN) Head = 70m, Q =137 cmh. KIRLOSKAR make horz. End suction back pull out pump in CI construction with cast iron impeller running at 3000 rpm bareshaft along with accessories like fabricated MS base frame, coupling guard, foundation blots, spacer type. Make std. foot mounted TEFC three phase motor rating 60HP at 3000 rpm."," 344,330.00 "," 688,660.00 "," 2.00 "," 309,897.00 "," 619,794.00 "," - "," - "," - "," 68,866.00 ",""," 426,313.03 ","",""],["80","1.00","Supply, erection, testing & commissioning of electrical motor driven Jockey pump of horizontal centrifugal end suction back pull out type with gland packing and capable to deliver 180LPM at 70MWC head 70m q 10.8 cmh .The pump shall be coupled to motor of suitable HP with speed of 3000RPM and complete set shall mounted on common base frame. The quoted rate shall includes providing & fixing of coupling, coupling gaurd and Foundation bolts etc."," 104,565.00 "," 104,565.00 "," 1.00 "," 94,108.50 "," 94,108.50 "," - "," - "," - "," 10,456.50 ",""," 64,730.32 ","",""],["81","1.00","Supply, erection, testing & commissioning of automatic starter panel for main pump, stand by pump & jockey pumps to suit the motor H.P. complete as per standard specification. The quoted rate shall includes providing & fixing of coupling, coupling gaurd and Foundation bolts etc."," 157,845.00 "," 157,845.00 "," 1.00 "," 142,060.50 "," 142,060.50 "," - "," - "," - "," 15,784.50 ",""," 97,713.92 ","",""],["133","4675.00","Supplying and Laying Hydralicliy Pressed Polished ( Rubber Mould ) Plain/ Colour Concrete Paver Block 60 mm Thick M20 Grade"," 770.00 "," 3,599,750.00 "," 1,353.76 "," 693.00 "," 938,155.68 "," - "," 3,321.24 "," - "," 2,661,594.32 "," 1,353.76 "," 728,593.63 ","",""],["134","601.00","Paving the floor with best approved quality flamed Granite Stone Slabs of size 1200 x 600 of 18 / 20mm thick with machine cut edges laid over a cement mortar bed of 20mm thick using cement mortar 1:3 (One cement and three sand) fixing the slabs with required cement slurry and laid in true right angles with minimum possible width of joints and pointing the joints with white cement mixed with mathcing colouring pigments etc., The granite stone slabs and other materials to be used shall be got approved by the Executive Engineer concerned before use on work, etc., complete as per standard speicification. "," 2,780.00 "," 1,670,780.00 "," 599.58 ",""," 1,666,832.40 "," - "," 1.42 "," - "," 3,947.60 "," 599.58 "," 137,068.78 ","",""],["135","186.00","Boring, providing and installing bored cast ?in situ under reamed reinforced cement concrete pile of 300mm diameter for a depth of 3m below the ground level to support the grade beam and column in cement concrete 1:11/2:3 (1 cement 1 � coarse sand : 3 graded stone aggregated of 20 mm nominal size) including the cost of steel reinforcement but including the cost of boring with casing with augur."," 2,250.00 "," 418,500.00 "," - ",""," - "," - "," 186.00 "," - "," 418,500.00 ","","","",""],[null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null]]
									;
						}
						function initData() {
							return [
									[
											"Name of work:   Construction of new Kalai Arangam in Omandurar Government Estate, Chennai -2. \n",
											"", "", "", "", "", "", "", "", "",
											"", "" ],
									[ "Sl. No.", "Quantity ", "DESCRIPTION",
											" PSK NEGO ", "", " TOTAL ", "",
											"", " QTY ", "", " AMOUNT ", "" ] ]
						}

						var $container = $("#example1");

						$container.handsontable({
							data : initData(),
							rowHeaders : true,
							colHeaders : true,
							contextMenu : true,
							manualColumnResize : true,
							manualRowResize : true,
							currentRowClassName : 'currentRow',
							currentColClassName : 'currentCol',
							mergeCells : [],
							fixedRowsTop : 0,
							fixedColumnsLeft : 0,
							minSpareRows : 1

						});

						$("#loadData").click(
								function() {
									var hotInstance = $("#example1")
											.handsontable('getInstance');
									var hot = JSON.stringify(hotInstance
											.getData());
									console.log(hot)
									hotInstance.render();
									hotInstance.loadData(getNewData());

								});

						$("#saveEdition")
								.click(
										function() {
											var hotInstance = $("#example1")
													.handsontable('getInstance');
											var excelData = JSON
													.stringify(hotInstance
															.getData());
											console.log(excelData);
											$
													.ajax({
														type : "POST",
														url : "saveEdition.do",
														contentType : "application/json; charset=utf-8",
														cache : false,
														data : JSON.stringify({
															edition : excelData
														}),
														success : function(response) {
															var obj = JSON.parse(JSON.stringify(response));
															document.getElementById("editionData").innerHTML = obj.edition;
														}
													});
										});
					});
</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="example1" class="hot handsontable"
		style="overflow: hidden; width: 1250px; height: 400px;"></div>
	<input class="button" type="button" id="loadData" value="load" />
	<input class="button" type="button" id="saveEdition" value="save" />
	<div id="savedItem">
		<b>console:</b>
		<p id="editionData"></p>
	</div>
</body>