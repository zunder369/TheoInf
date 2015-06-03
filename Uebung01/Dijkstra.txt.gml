Creator	"yFiles"
Version	"2.11"
graph
[
	hierarchic	1
	label	""
	directed	1
	node
	[
		id	0
		label	"A"
		graphics
		[
			x	225.0
			y	75.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"A"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	node
	[
		id	1
		label	"B"
		graphics
		[
			x	150.0
			y	0.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"B"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	node
	[
		id	2
		label	"C"
		graphics
		[
			x	0.0
			y	150.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"C"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	node
	[
		id	3
		label	"D"
		graphics
		[
			x	75.0
			y	225.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"D"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	node
	[
		id	4
		label	"E"
		graphics
		[
			x	150.0
			y	225.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"E"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	node
	[
		id	5
		label	"F"
		graphics
		[
			x	225.0
			y	150.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"F"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	node
	[
		id	6
		label	"G"
		graphics
		[
			x	150.0
			y	75.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"G"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	node
	[
		id	7
		label	"H"
		graphics
		[
			x	150.0
			y	150.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"H"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	node
	[
		id	8
		label	"I"
		graphics
		[
			x	75.0
			y	150.0
			w	30.0
			h	30.0
			type	"rectangle"
			fill	"#FFCC00"
			outline	"#000000"
		]
		LabelGraphics
		[
			text	"I"
			fontSize	12
			fontName	"Dialog"
			model	"null"
		]
	]
	edge
	[
		source	0
		target	1
		label	"2"
		graphics
		[
			fill	"#000000"
			Line
			[
				point
				[
					x	225.0
					y	75.0
				]
				point
				[
					x	225.0
					y	0.0
				]
				point
				[
					x	150.0
					y	0.0
				]
			]
		]
		edgeAnchor
		[
			ySource	-1.0
			xTarget	1.0
		]
		LabelGraphics
		[
			text	"2"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	0
		target	5
		label	"9"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			ySource	1.0
			yTarget	-1.0
		]
		LabelGraphics
		[
			text	"9"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	0
		target	6
		label	"15"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			xSource	-1.0
			xTarget	1.0
		]
		LabelGraphics
		[
			text	"15"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	1
		target	2
		label	"4"
		graphics
		[
			fill	"#000000"
			Line
			[
				point
				[
					x	150.0
					y	0.0
				]
				point
				[
					x	0.0
					y	0.0
				]
				point
				[
					x	0.0
					y	150.0
				]
			]
		]
		edgeAnchor
		[
			xSource	-1.0
			yTarget	-1.0
		]
		LabelGraphics
		[
			text	"4"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	1
		target	6
		label	"6"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			ySource	1.0
			yTarget	-1.0
		]
		LabelGraphics
		[
			text	"6"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	2
		target	3
		label	"2"
		graphics
		[
			fill	"#000000"
			Line
			[
				point
				[
					x	0.0
					y	150.0
				]
				point
				[
					x	0.0
					y	225.0
				]
				point
				[
					x	75.0
					y	225.0
				]
			]
		]
		edgeAnchor
		[
			ySource	1.0
			xTarget	-1.0
		]
		LabelGraphics
		[
			text	"2"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	2
		target	8
		label	"15"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			xSource	1.0
			xTarget	-1.0
		]
		LabelGraphics
		[
			text	"15"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	3
		target	4
		label	"1"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			xSource	1.0
			xTarget	-1.0
		]
		LabelGraphics
		[
			text	"1"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	3
		target	8
		label	"1"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			ySource	-1.0
			yTarget	1.0
		]
		LabelGraphics
		[
			text	"1"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	4
		target	5
		label	"6"
		graphics
		[
			fill	"#000000"
			Line
			[
				point
				[
					x	150.0
					y	225.0
				]
				point
				[
					x	225.0
					y	225.0
				]
				point
				[
					x	225.0
					y	150.0
				]
			]
		]
		edgeAnchor
		[
			xSource	1.0
			yTarget	1.0
		]
		LabelGraphics
		[
			text	"6"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	4
		target	7
		label	"3"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			ySource	-1.0
			yTarget	1.0
		]
		LabelGraphics
		[
			text	"3"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	5
		target	7
		label	"11"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			xSource	-1.0
			xTarget	1.0
		]
		LabelGraphics
		[
			text	"11"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	6
		target	7
		label	"15"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			ySource	1.0
			yTarget	-1.0
		]
		LabelGraphics
		[
			text	"15"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	6
		target	8
		label	"2"
		graphics
		[
			fill	"#000000"
			Line
			[
				point
				[
					x	150.0
					y	75.0
				]
				point
				[
					x	75.0
					y	75.0
				]
				point
				[
					x	75.0
					y	150.0
				]
			]
		]
		edgeAnchor
		[
			xSource	-1.0
			yTarget	-1.0
		]
		LabelGraphics
		[
			text	"2"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
	edge
	[
		source	7
		target	8
		label	"4"
		graphics
		[
			fill	"#000000"
		]
		edgeAnchor
		[
			xSource	-1.0
			xTarget	1.0
		]
		LabelGraphics
		[
			text	"4"
			fontSize	12
			fontName	"Dialog"
			model	"six_pos"
			position	"tail"
		]
	]
]
