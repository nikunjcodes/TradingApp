import { Button } from '@/components/ui/button';
import React from 'react'
import ReactApexChart from 'react-apexcharts'
const timeSeries=[
    {
        keyword: "DIGITAL_CURRENCY_DAILY",
        key:"TIME SERIES (DAILY)",
        lable:"1 Day",
        value: 1,
    },
    {
        keyword: "DIGITAL_CURRENCY_WEEKLY",
        key:"TIME SERIES (WEEKLY)",
        lable:"1 Week",
        value: 7,
    },
    {
        keyword: "DIGITAL_CURRENCY_MONTHLY",
        key:"TIME SERIES (MONTHLY)",
        lable:"1 Month",
        value: 30,
    }

]
const StockChart = () => {
    const [activeLable  , setActiveLable] = React.useState("1 Day")
  const series = [
    {
      name: 'Stock Price', // You can give a name to your series for clarity
      data: [
        [1730556489076, 69312.3212739771],
        [1730560576224, 69183.8287781954],
        [1730563549546, 69412.9332576336],
        [1730567716166, 69519.9993897261],
        [1730571552465, 69268.5415635025],
        [1730574327562, 69413.3617711876],
        [1730578530448, 69482.8031308654],
        [1730581995505, 69459.2861779806],
        [1730585370723, 69387.3920598458],
        [1730589031814, 69258.7473726412],
        [1730593283343, 69362.0766661086],
        [1730596293666, 69123.0462198424],
        [1730599560427, 68922.6957934696],
        [1730603583382, 68339.8798861364],
        [1730606700973, 68324.7189184599],
        [1730610384127, 68573.7088243111],
        [1730613947380, 68446.9749320461],
        [1730617403682, 68549.8788069817],
        [1730621031280, 68367.6932734761],
        [1730624463046, 68450.3728850148],
        [1730628330678, 68200.4911176451],
        [1730632185715, 68373.7600028149],
        [1730635810377, 68370.9001850339],
        [1730639321654, 68390.716180988],
        [1730643546006, 68048.9190831791],
        [1730647116075, 67719.1381356033],
        [1730649769661, 68096.6833200844],
        [1730653873631, 68439.1424161653],
        [1730657079169, 68340.8570710041],
        [1730661113348, 68313.7653901177],
        [1730665146583, 68533.5781173907],
        [1730668855751, 69102.9164949208],
        [1730671546658, 69113.856536758],
        [1730674825756, 68910.2563500219],
        [1730678732205, 68736.0183301731],
        [1730682100434, 68593.9403367128],
        [1730686663496, 68850.9066242415],
        [1730689557404, 69317.9848723955],
        [1730692947542, 68980.294467579],
        [1730697591518, 69077.4467798451],
        [1730700870128, 68909.5696667875],
        [1730704712826, 68985.503748709],
        [1730708330159, 68639.8228568942],
        [1730711457629, 68816.4047995141],
        [1730714991523, 68795.7748895565],
      ]
    }
  ];

  const options = {
    chart: {
      id: "area-datetime",
      type: "area",
      height: 450,
      zoom: {
        autoScaleYaxis: true
      }
    },
    dataLabels: {
      enabled: false
    },
    xaxis: {
      type: "datetime",
      tickAmount: 6
    },
    colors: ["#758AA2"],
    markers: {
      colors: ["#fff"],
      strokeColor: "#fff",
      size: 0,
      strokeWidth: 1,
      style: "hollow"
    },
    tooltip: {
      theme: "dark",  // Using a dark theme for better visibility
      style: {
        fontSize: "12px", // Font size
        fontFamily: "Arial, Helvetica, sans-serif", // Font style
        color: "#fff",  // Text color (white)
      },
      onDatasetHover: {
        highlightDataSeries: true,
      },
      marker: {
        show: true, // Show the marker on tooltip
      },
      x: {
        show: true, // Display the X-axis value
      },
      y: {
        title: {
          formatter: function (seriesName) {
            return "Price"; // Customize the title
          }
        }
      },
      fixed: {
        enabled: false,  // Tooltip will move with the cursor
        position: 'topRight',
        offsetX: 0,
        offsetY: 0
      }
    },
    grid: {
      borderColor: "#47535E",
      strokeDashArray: 4,
      show: true
    }
  };
  const handleActiveLable=(value)=>{
        setActiveLable(value);
  }

  return (
    <div>
        <div className='space-x-3'>
            {timeSeries.map((item)=>
            <Button variant = {activeLable== item.lable ? "default" : "outline"} onClick={()=>handleActiveLable(item.lable)} key={item.lable} className="rounded-full" >
                {item.lable}
            </Button>
            )}
        </div>
      <div id='chart-timelines'>
        <ReactApexChart options={options} series={series} type="area" height={450} />
      </div>
    </div>
  );
};

export default StockChart;
