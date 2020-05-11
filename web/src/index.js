import React from 'react';
import { render } from 'react-dom';
import Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';




fetch('http://localhost:8090/messages').then(res => res.json())
.then(data => {

    var offset =1;

    const options = {
    /*  chart: {
        events: {
            load: function () {
                // set up the updating of the chart each second
                var series = this.series[0];
                setInterval(function () {
                    var x = series.xIncrement + offset++, 
                        y = series.dataMax;
                    series.addPoint([x, y], true, true);
                }, 1000);
            }
        }
      },*/
      title: {
        text: 'Sensors'
      },
      series: [
        { name:'sensor1', data: data.filter(x => x.label === 'sensor1').map(x => x.value)},
        { name:'sensor2', data: data.filter(x => x.label === 'sensor2').map(x => x.value)},
        { name:'sensor3', data: data.filter(x => x.label ===  'sensor3').map(x => x.value)},
        { name:'sensor4', data: data.filter(x => x.label === 'sensor4').map(x => x.value)},
      ]
    };


    const App = () => (
      <div>
        <HighchartsReact highcharts={Highcharts} options={options} />
      </div>
    );

    render(<App />, document.getElementById('root'));
    }
);
