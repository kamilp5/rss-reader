import DateTimeFormat = Intl.DateTimeFormat;

export interface RssItem{
  id: number;
  title: string;
  url: string;
  imageUrl: string;
  description: string;
  date: DateTimeFormat;
}
