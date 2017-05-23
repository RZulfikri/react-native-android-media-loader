import React, { PropTypes, Component } from 'react';

const { RNMediaLoader: mediaLoader } = NativeModules;

export class MediaLoader extends Component{
    static propTypes = {
        limit : PropTypes.number,
        nextId : PropTypes.number,
    }

    static defaultProps = {
        limit : 0,
        nextId : -1,
    }

    getMedia() {
        mediaLoader
            .getMediaList(this.props.limit, this.props.nextId)
            .then((result) => console.log(result))
    }
}